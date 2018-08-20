package map;

public class SimpleHashMapMain {

	public static void main(String[] args) {
		SimpleHashMap<Integer, Integer> map = new SimpleHashMap<Integer, Integer>(3);
		map.put(10, 10);
		map.put(65, 20);
		map.put(30, 30);
		map.put(40, 40);
		map.put(50, 50);
		map.put(50, 10);
		System.out.println(map.show());

	}

}
