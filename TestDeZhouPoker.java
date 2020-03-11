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

	public String compete(String black, String white) {
		CardType bCardType = cardsType(black);
		CardType wCardType = cardsType(white);
		if (cardValue.get(bCardType) == cardValue.get(wCardType)) {
			return null;
		} else {
			if (cardValue.get(bCardType) > cardValue.get(wCardType)) {
				return "Black wins";
			} else {
				return "White wins";
			}
		}
	}

	// �ж��˿��Ƶ����� ͬ��˳����֧����«��ͬ����˳�ӣ����������ԣ����ӣ�ɢ��
	public CardType cardsType(String card) {
		CardType type = null;
		String[] cards = card.split(" ");
		// �洢����
		Integer[] numbers = new Integer[5];
		// �洢��ɫ
		Character[] flattern = new Character[5];
		// �����ֺͻ�ɫ���벢�洢
		for (int i = 0; i < cards.length; i++) {
			Character c = cards[i].charAt(0);
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
				integer = Integer.valueOf(cards[i].charAt(0));
			}
			numbers[i] = integer;
			flattern[i] = cards[i].charAt(1);
		}
		Arrays.sort(numbers);
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
	String tieZhi = "AD AS AC AH 2D";
	String huLu = "3D 3S 5D 5S 5C";
	String tongHua = "2D 4D 6D 8D AD";
	String shunZi = "AD KH QC JS TD";
	String sanTiao = "AH AS AD 2D 6H";
	String liangDui = "3H 3S 4H 4S 5D";
	String duiZi = "3H 3S 4H 6S 7D";
	String sanPai = "3H 5S 7D 9S JC";

	@Test
	public void twoDiffTypeShun() {
		 assertEquals(testObject.compete(sanPai,tieZhi), "White wins");
		 assertEquals(testObject.compete(duiZi,tieZhi ), "White wins");
		 assertEquals(testObject.compete(liangDui,tieZhi ), "White wins");
		 assertEquals(testObject.compete(sanTiao,tieZhi ), "White wins");
		assertEquals(testObject.compete(shunZi, tieZhi), "White wins");
		 assertEquals(testObject.compete(tongHua,tieZhi ), "White wins");
		 assertEquals(testObject.compete(huLu,tieZhi ), "White wins");
		assertEquals(testObject.compete(tongHuaShun, tieZhi), "Black wins");
	}
}
