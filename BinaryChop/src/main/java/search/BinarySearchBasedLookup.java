package search;

public class BinarySearchBasedLookup {
	public int find(int[] containerToSearch, int lookedUpValue) {
		if (containerToSearch.length == 0) {
			return -1;
		}

		for(int i = 0; i < containerToSearch.length; i++) {
			if (containerToSearch[i] == lookedUpValue) {
				return i;
			}
		}

		return -1;
	}
}
