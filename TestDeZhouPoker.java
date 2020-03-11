package dezhoupoker;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

enum CardType {
	TONGHUASHUN, TIEZHI, HULU, TONGHUA, SHUNZI, SANTIAO, LIANGDUI, DUIZI, SANPAI
}

enum Result {
	White_wins, Black_wins, Tie
}

class DeZhouPoker {
	HashMap<CardType, Integer> cardValue = new HashMap<>();

	public DeZhouPoker() {
		int val = 0;
		cardValue.put(CardType.SANPAI, val);
		val++;
		cardValue.put(CardType.DUIZI, val);
		val++;
		cardValue.put(CardType.LIANGDUI, val);
		val++;
		cardValue.put(CardType.SANTIAO, val);
		val++;
		cardValue.put(CardType.SHUNZI, val);
		val++;
		cardValue.put(CardType.TONGHUA, val);
		val++;
		cardValue.put(CardType.HULU, val);
		val++;
		cardValue.put(CardType.TIEZHI, val);
		val++;
		cardValue.put(CardType.TONGHUASHUN, val);

	}

	public Result compete(String black, String white) {
		CardType bCardType = cardsType(black);
		CardType wCardType = cardsType(white);
		// System.out.println(bCardType);
		if (cardValue.get(bCardType) > cardValue.get(wCardType)) {
			return Result.Black_wins;
		} else if (cardValue.get(bCardType) < cardValue.get(wCardType)) {
			return Result.White_wins;
		} else {
			Result result = null;
			switch (bCardType) {
			case TONGHUASHUN:
				result = tongHuaShunRule(black, white);
				break;
			case TIEZHI:
				result = tieZhiRule(black, white);
				break;
			case HULU:
				result = huLuRule(black, white);
				break;
			case TONGHUA:
				result = tongHuaRule(black, white);
				break;
			case SHUNZI:
				result = shunZiRule(black, white);
				break;
			case SANTIAO:
				result = sanTiaoRule(black, white);
				break;
			case LIANGDUI:
				result = liangDuiRule(black, white);
				break;
			case DUIZI:
				result = duiZiRule(black, white);
				break;
			default:
				result = sanPaiRule(black, white);
				break;
			}
			return result;
		}
	}

	private Result duiZiRule(String black, String white) {
		Result result = null;
		Integer[] bNumbers = getNumbers(black);
		Integer[] wNumbers = getNumbers(white);
		HashMap<Integer, Integer> bNumOfEquals = numOfEquals(bNumbers);
		HashMap<Integer, Integer> wNumOfEquals = numOfEquals(wNumbers);
		Integer bInteger = getKey(bNumOfEquals, 2).get(0);
		Integer wInteger = getKey(wNumOfEquals, 2).get(0);
		if (bInteger > wInteger) {
			result = Result.Black_wins;
		} else if (bInteger < wInteger) {
			result = Result.White_wins;
		} else {
			
			Integer[] b2Numbers = new Integer[3];
			Integer[] w2Numbers = new Integer[3];
			int blength = 0;
			int wlength = 0;
			for (Integer cur : bNumbers) {
				if(cur!=bInteger){
					b2Numbers[blength++] = cur;
				}
			}
			for (Integer cur : wNumbers) {
				if(cur!=wInteger){
					w2Numbers[wlength++] = cur;
				}
			}
			Arrays.sort(b2Numbers);
			Arrays.sort(w2Numbers);
			int i=b2Numbers.length-1;
			for(;i>=0;i--){
				if (b2Numbers[i] > w2Numbers[i]) {
					result = Result.Black_wins;
					break;
				} else if (b2Numbers[i] < w2Numbers[i]) {
					result = Result.White_wins;
					break;
				}
			}
			if(i==-1){
				result = Result.Tie;
			}
		}
		return result;
	}

	private Result liangDuiRule(String black, String white) {
		Result result = null;
		Integer[] bNumbers = getNumbers(black);
		Integer[] wNumbers = getNumbers(white);
		HashMap<Integer, Integer> bNumOfEquals = numOfEquals(bNumbers);
		HashMap<Integer, Integer> wNumOfEquals = numOfEquals(wNumbers);
		List<Integer> bList = getKey(bNumOfEquals, 2);
		List<Integer> wList = getKey(wNumOfEquals, 2);
		Collections.sort(bList);
		Collections.sort(wList);
		if (bList.get(1) > wList.get(1)) {
			result = Result.Black_wins;
		} else if (bList.get(1) < wList.get(1)) {
			result = Result.White_wins;
		} else {
			if (bList.get(0) > wList.get(0)) {
				result = Result.Black_wins;
			} else if (bList.get(0) < wList.get(0)) {
				result = Result.White_wins;
			} else {
				List<Integer> b1List = getKey(bNumOfEquals, 1);
				List<Integer> w1List = getKey(wNumOfEquals, 1);
				if (b1List.get(0) > w1List.get(0)) {
					result = Result.Black_wins;
				} else if (b1List.get(0) < w1List.get(0)) {
					result = Result.White_wins;
				} else {
					result = Result.Tie;
				}
			}
		}
		return result;
	}

	private Result sanTiaoRule(String black, String white) {
		Result result = null;
		Integer[] bNumbers = getNumbers(black);
		Integer[] wNumbers = getNumbers(white);
		HashMap<Integer, Integer> bNumOfEquals = numOfEquals(bNumbers);
		HashMap<Integer, Integer> wNumOfEquals = numOfEquals(wNumbers);
		if (getKey(bNumOfEquals, 3).get(0) > getKey(wNumOfEquals, 3).get(0)) {
			result = Result.Black_wins;
		} else if (getKey(bNumOfEquals, 3).get(0) < getKey(wNumOfEquals, 3).get(0)) {
			result = Result.White_wins;
		} else {
			result = Result.Tie;
		}
		return result;
	}

	private Result shunZiRule(String black, String white) {
		Result result = null;
		Integer[] bNumbers = getNumbers(black);
		Integer[] wNumbers = getNumbers(white);
		if (bNumbers[4] > wNumbers[4]) {
			result = Result.Black_wins;
		} else if (bNumbers[4] < wNumbers[4]) {
			result = Result.White_wins;
		} else {
			result = Result.Tie;
		}
		return result;
	}

	private Result tongHuaRule(String black, String white) {
		return sanPaiRule(black, white);
	}

	public Result tongHuaShunRule(String black, String white) {
		return shunZiRule(black, white);
	}

	public Result tieZhiRule(String black, String white) {
		Result result = null;
		Integer[] bNumbers = getNumbers(black);
		Integer[] wNumbers = getNumbers(white);
		HashMap<Integer, Integer> bNumOfEquals = numOfEquals(bNumbers);
		HashMap<Integer, Integer> wNumOfEquals = numOfEquals(wNumbers);
		if (getKey(bNumOfEquals, 4).get(0) > getKey(wNumOfEquals, 4).get(0)) {
			result = Result.Black_wins;
		} else if (getKey(bNumOfEquals, 4).get(0) < getKey(wNumOfEquals, 4).get(0)) {
			result = Result.White_wins;
		} else {
			result = Result.Tie;
		}
		return result;

	}

	private Result huLuRule(String black, String white) {
		Result result = null;
		Integer[] bNumbers = getNumbers(black);
		Integer[] wNumbers = getNumbers(white);
		HashMap<Integer, Integer> bNumOfEquals = numOfEquals(bNumbers);
		HashMap<Integer, Integer> wNumOfEquals = numOfEquals(wNumbers);
		if (getKey(bNumOfEquals, 3).get(0) > getKey(wNumOfEquals, 3).get(0)) {
			result = Result.Black_wins;
		} else if (getKey(bNumOfEquals, 3).get(0) < getKey(wNumOfEquals, 3).get(0)) {
			result = Result.White_wins;
		} else {
			result = Result.Tie;
		}
		return result;
	}

	private Result sanPaiRule(String black, String white) {
		Result result = null;
		Integer[] bNumbers = getNumbers(black);
		Integer[] wNumbers = getNumbers(white);
		int i = bNumbers.length - 1; 
		for (; i >= 0; i--) {
			if (bNumbers[i] > wNumbers[i]) {
				result = Result.Black_wins;
				break;
			} else if (bNumbers[i] < wNumbers[i]) {
				result = Result.White_wins;
				break;
			}
		}
		if (i == -1) {
			result = Result.Tie;
		}
		return result;
	}

	// 分离出数字

	public Integer[] getNumbers(String card) {
		String[] cards = card.split(" ");
		Integer[] numbers = new Integer[5];
		for (int i = 0; i < cards.length; i++) {
			Character c = cards[i].trim().charAt(0);
			Integer integer;
			if (c == 'T') {
				integer = 10;
			} else if (c == 'J') {
				integer = 11;
			} else if (c == 'Q') {
				integer = 12;
			} else if (c == 'K') {
				integer = 13;
			} else if (c == 'A') {
				integer = 14;
			} else {
				integer = Integer.valueOf(cards[i].charAt(0) - '0');
			}
			numbers[i] = integer;
		}
		Arrays.sort(numbers);
		return numbers;
	}

	// 分离出花色
	public Character[] getFlatterns(String card) {
		String[] cards = card.split(" ");
		Character[] flatterns = new Character[5];
		for (int i = 0; i < cards.length; i++) {
			flatterns[i] = cards[i].charAt(1);
		}
		return flatterns;
	}

	// 判断扑克牌的类型 同花顺＞铁支＞葫芦＞同花＞顺子＞三条＞两对＞对子＞散牌
	public CardType cardsType(String card) {
		CardType type = null;
		// 把数字和花色分离并存储
		Integer[] numbers = getNumbers(card);
		Character[] flattern = getFlatterns(card);

		HashMap<Integer, Integer> map = numOfEquals(numbers);
		// 条件
		// 同花
		boolean tongHua = flattern[0] == flattern[1] && flattern[1] == flattern[2] && flattern[2] == flattern[3]
				&& flattern[3] == flattern[4];
		// 顺子
		boolean shunZi = ((numbers[4] - numbers[3] == 1) && (numbers[3] - numbers[2] == 1)
				&& (numbers[2] - numbers[1] == 1) && (numbers[1] - numbers[0] == 1))
				|| (numbers.toString().equals("A2345"));

		// 铁枝
		boolean tieZhi = map.containsValue(4);

		// 三条
		boolean sanTiao = map.containsValue(3);
		// 对子
		boolean duiZi = map.containsValue(2);
		// 葫芦
		boolean huLu = sanTiao && duiZi;
		// 两对
		int timeOfDuizi = 0;
		// 对子出现了几次
		if (duiZi) {
			Set<Integer> keySet = map.keySet();
			for (Integer key : keySet) {
				Integer times = map.get(key);
				if (times == 2) {
					timeOfDuizi++;
				}
			}
		}
		boolean liangDui = timeOfDuizi == 2;

		// 同花顺
		if (tongHua && shunZi) {
			type = CardType.TONGHUASHUN;
		}
		// 铁枝
		else if (tieZhi) {
			type = CardType.TIEZHI;
		}
		// 葫芦
		else if (huLu) {
			type = CardType.HULU;
		}
		// 同花
		else if (tongHua) {
			type = CardType.TONGHUA;
		}
		// 顺子
		else if (shunZi) {
			type = CardType.SHUNZI;
		} else if (sanTiao) {
			type = CardType.SANTIAO;
		} else if (liangDui) {
			type = CardType.LIANGDUI;
		} else if (duiZi) {
			type = CardType.DUIZI;
		} else {
			type = CardType.SANPAI;
		}

		return type;
	}

	// 统计相同的数字{(number,times)...}
	public HashMap<Integer, Integer> numOfEquals(Integer[] integers) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < integers.length; i++) {
			if (map.get(integers[i]) != null) {
				map.put(integers[i], map.get(integers[i]) + 1);
			} else {
				map.put(integers[i], 1);
			}
		}
		return map;
	}

	// 由value 得key
	public List<Integer> getKey(Map<Integer, Integer> map, Integer value) {
		List<Integer> keyList = new ArrayList<>();
		for (Integer key : map.keySet()) {
			if (map.get(key).equals(value)) {
				keyList.add(key);
			}
		}
		return keyList;
	}
}

public class TestDeZhouPoker {
	DeZhouPoker testObject = new DeZhouPoker();
	String tongHuaShun = "AD KD JD QD TD";
	String tongHuaShun2 = "2D 3D 4D 5D 6D";

	String tieZhi = "AD AS AC AH 2D";
	String tieZhi2 = "KD KS KC KH 2C";

	String huLu = "4D 4S 6D 6S 6C";
	String huLu2 = "3D 3S 5D 5S 5C";

	String tongHua = "2D 4D 6D TD AD";
	String tongHua2 = "2H 4H 6H 8H AH";

	String shunZi = "AD KH QC JS TD";
	String shunZi2 = "3D 4H 5C 6S 7D";

	String sanTiao = "AH AS AD 2D 6H";
	String sanTiao2 = "KH KS KD 3D 4H";

	String liangDui = "5H 6S 6H 4C 5D";
	String liangDui2 = "3H 3S 4H 4S 5D";
	String liangDui3 = "5H 6S 6H 4C 5D";
	String liangDui4 = "3H 3S 6K 6D 5D";
	String liangDui5 = "5H 6S 6H 4C 5D";
	String liangDui6 = "5K 5S 6K 6D 2D";

	String duiZi = "6D 3C 4H 6H 7C";
	String duiZi2 = "3H 3S 4C 6S 7D";
	String duiZi3 = "6D 3C 4H 6H 8C";
	String duiZi4 = "6C 3S 4H 6S 7D";
	
	String sanPai = "4H 6S 8D 2S AC";
	String sanPai2 = "3H 5S 7D 9S JC";

	@Test
	public void twoDiffTypeShun() {
		assertEquals(testObject.compete(sanPai2, tieZhi), Result.White_wins);
		assertEquals(testObject.compete(duiZi2, tieZhi), Result.White_wins);
		assertEquals(testObject.compete(liangDui2, tieZhi), Result.White_wins);
		assertEquals(testObject.compete(sanTiao2, tieZhi), Result.White_wins);
		assertEquals(testObject.compete(shunZi2, tieZhi), Result.White_wins);
		assertEquals(testObject.compete(tongHua2, tieZhi), Result.White_wins);
		assertEquals(testObject.compete(huLu2, tieZhi), Result.White_wins);
		assertEquals(testObject.compete(tongHuaShun2, tieZhi), Result.Black_wins);
	}

	@Test
	public void twoTongHuaShun() {
		assertEquals(testObject.compete(tongHuaShun, tongHuaShun2), Result.Black_wins);
		assertEquals(testObject.compete(tongHuaShun2, tongHuaShun2), Result.Tie);
		assertEquals(testObject.compete(tongHuaShun2, tongHuaShun), Result.White_wins);

	}

	@Test
	public void twoTieZhi() {
		assertEquals(testObject.compete(tieZhi, tieZhi2), Result.Black_wins);
		assertEquals(testObject.compete(tieZhi2, tieZhi2), Result.Tie);
		assertEquals(testObject.compete(tieZhi2, tieZhi), Result.White_wins);

	}

	@Test
	public void twoHuLu() {
		assertEquals(testObject.compete(huLu, huLu2), Result.Black_wins);
		assertEquals(testObject.compete(huLu2, huLu2), Result.Tie);
		assertEquals(testObject.compete(huLu2, huLu), Result.White_wins);
	}

	@Test
	public void twoTongHua() {
		assertEquals(testObject.compete(tongHua, tongHua2), Result.Black_wins);
		assertEquals(testObject.compete(tongHua2, tongHua2), Result.Tie);
		assertEquals(testObject.compete(tongHua2, tongHua), Result.White_wins);
	}

	@Test
	public void twoShunZi() {
		assertEquals(testObject.compete(shunZi, shunZi2), Result.Black_wins);
		assertEquals(testObject.compete(shunZi2, shunZi2), Result.Tie);
		assertEquals(testObject.compete(shunZi2, shunZi), Result.White_wins);

	}

	@Test
	public void twoSanTiao() {
		assertEquals(testObject.compete(sanTiao, sanTiao2), Result.Black_wins);
		assertEquals(testObject.compete(sanTiao2, sanTiao2), Result.Tie);
		assertEquals(testObject.compete(sanTiao2, sanTiao), Result.White_wins);

	}

	@Test
	public void twoLiangDui() {
		assertEquals(testObject.compete(liangDui, liangDui2), Result.Black_wins);
		assertEquals(testObject.compete(liangDui2, liangDui2), Result.Tie);
		assertEquals(testObject.compete(liangDui2, liangDui), Result.White_wins);
		assertEquals(testObject.compete(liangDui3, liangDui4), Result.Black_wins);
		assertEquals(testObject.compete(liangDui5, liangDui6), Result.Black_wins);
	}

	@Test
	public void twoDuiZi() {
		assertEquals(testObject.compete(duiZi, duiZi2), Result.Black_wins);
		assertEquals(testObject.compete(duiZi2, duiZi2), Result.Tie);
		assertEquals(testObject.compete(duiZi2, duiZi), Result.White_wins);
		
		assertEquals(testObject.compete(duiZi3, duiZi4), Result.Black_wins);
	}
	@Test
	public void twoSanPai(){
		assertEquals(testObject.compete(sanPai, sanPai2), Result.Black_wins);
		assertEquals(testObject.compete(sanPai2, sanPai2), Result.Tie);
		assertEquals(testObject.compete(sanPai2, sanPai), Result.White_wins);
	}
}
