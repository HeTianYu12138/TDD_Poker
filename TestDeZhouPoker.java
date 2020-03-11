package dezhoupoker;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
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

	public Result tongHuaRule(String black, String white) {
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
				result = tongHuaRule(black, white);
				break;
			default:
				break;
			}
			return result;
		}
	}

	// ���������
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
				integer = Integer.valueOf(cards[i].charAt(0)-'0');
			}
			numbers[i] = integer;
		}
		Arrays.sort(numbers);
		return numbers;
	}

	// �������ɫ
	public Character[] getFlatterns(String card) {
		String[] cards = card.split(" ");
		Character[] flatterns = new Character[5];
		for (int i = 0; i < cards.length; i++) {
			flatterns[i] = cards[i].charAt(1);
		}
		return flatterns;
	}

	// �ж��˿��Ƶ����� ͬ��˳����֧����«��ͬ����˳�ӣ����������ԣ����ӣ�ɢ��
	public CardType cardsType(String card) {
		CardType type = null;
		// �����ֺͻ�ɫ���벢�洢
		Integer[] numbers = getNumbers(card);
		Character[] flattern = getFlatterns(card);

		HashMap<Integer, Integer> map = numOfEquals(numbers);
		// ����
		// ͬ��
		boolean tongHua = flattern[0] == flattern[1] && flattern[1] == flattern[2] && flattern[2] == flattern[3]
				&& flattern[3] == flattern[4];
		// ˳��
		boolean shunZi = ((numbers[4] - numbers[3] == 1) && (numbers[3] - numbers[2] == 1)
				&& (numbers[2] - numbers[1] == 1) && (numbers[1] - numbers[0] == 1))
				|| (numbers.toString().equals("A2345"));

		// ��֦
		boolean tieZhi = map.containsValue(4);

		// ����
		boolean sanTiao = map.containsValue(3);
		// ����
		boolean duiZi = map.containsValue(2);
		// ��«
		boolean huLu = sanTiao && duiZi;
		// ����
		int timeOfDuizi = 0;
		// ���ӳ����˼���
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

		// ͬ��˳
		if (tongHua && shunZi) {
			type = CardType.TONGHUASHUN;
		}
		// ��֦
		else if (tieZhi) {
			type = CardType.TIEZHI;
		}
		// ��«
		else if (huLu) {
			type = CardType.HULU;
		}
		// ͬ��
		else if (tongHua) {
			type = CardType.TONGHUA;
		}
		// ˳��
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

	// ͳ����ͬ������{(num,times)}
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
}

public class TestDeZhouPoker {
	// HDSC
	DeZhouPoker testObject = new DeZhouPoker();
	String tongHuaShun = "AD KD JD QD TD";
	String tongHuaShun2 = "2D 3D 4D 5D 6D";

	String tieZhi = "AD AS AC AH 2D";
	String tieZhi2 = "KD KS KC KH 2C";

	String huLu = "3D 3S 5D 5S 5C";
	String huLu2 = "4D 4S 6D 6S 6C";

	String tongHua = "2D 4D 6D 8D AD";
	String tongHua2 = "2H 4H 6H TH AH";

	String shunZi = "AD KH QC JS TD";
	String shunZi2 = "3D 4H 5C 6S 7D";

	String sanTiao = "AH AS AD 2D 6H";
	String sanTiao2 = "KH KS KD 3D 4H";

	String liangDui = "3H 3S 4H 4S 5D";
	String liangDui2 = "5H 6S 6H 4C 5D";

	String duiZi = "3H 3S 4H 6S 7D";
	String duiZi2 = "6D 3C 4H 6H 7C";

	String sanPai = "3H 5S 7D 9S JC";
	String sanPai2 = "4H 6S 8D 2S AC";

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
	}
}
