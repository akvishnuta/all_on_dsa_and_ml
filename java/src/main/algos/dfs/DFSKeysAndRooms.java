package dfs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * There are n rooms labeled from 0 to n - 1 and all the rooms are locked except for room 0. Your goal is to visit all the rooms. However, you cannot enter a locked room without having its key.
 *
 * When you visit a room, you may find a set of distinct keys in it. Each key has a number on it, denoting which room it unlocks, and you can take all of them with you to unlock the other rooms.
 *
 * Given an array rooms where rooms[i] is the set of keys that you can obtain if you visited room i, return true if you can visit all the rooms, or false otherwise.
 *
 *
 *
 * Example 1:
 *
 * Input: rooms = [[1],[2],[3],[]]
 * Output: true
 * Explanation:
 * We visit room 0 and pick up key 1.
 * We then visit room 1 and pick up key 2.
 * We then visit room 2 and pick up key 3.
 * We then visit room 3.
 * Since we were able to visit every room, we return true.
 * Example 2:
 *
 * Input: rooms = [[1,3],[3,0,1],[2],[0]]
 * Output: false
 * Explanation: We can not enter room number 2 since the only key that unlocks it is in that room.
 *
 *
 * Constraints:
 *
 * n == rooms.length
 * 2 <= n <= 1000
 * 0 <= rooms[i].length <= 1000
 * 1 <= sum(rooms[i].length) <= 3000
 * 0 <= rooms[i][j] < n
 * All the values of rooms[i] are unique.
 */
public class DFSKeysAndRooms {
    Set<Integer> visited = new HashSet<>();

    public boolean canVisitAllRooms(List<List<Integer>> rooms) {


        visited.add(0);
        List<Integer> keys = rooms.get(0);
        visit(rooms, keys);

        if(rooms.size() == visited.size()) {
            return true;
        }
        visited.clear();
        return false;

    }

    private void visit(List<List<Integer>> rooms, List<Integer> keys) {
        System.out.println("keys = " + keys);

        for (int i=0;i<keys.size();i++) {
            if(!visited.contains(keys.get(i))) {
                visited.add(keys.get(i));
                visit(rooms, rooms.get(keys.get(i)));
                // System.out.println("visited = " + visited);
            }

        }


    }

    public static void main(String[] args) {
        DFSKeysAndRooms keysAndRooms = new DFSKeysAndRooms();
        List<List<Integer>> rooms = List.of(
                List.of(1,3),
                List.of(3,0,1),
                List.of(2),
                List.of(0)
        );
        boolean result = keysAndRooms.canVisitAllRooms(rooms);
        System.out.println("result = " + result);//false


        //Input: rooms = [[1],[2],[3],[]]
        List<List<Integer>> rooms2 = List.of(
                List.of(1),
                List.of(2),
                List.of(3),
                List.of()
        );
        boolean result2 = keysAndRooms.canVisitAllRooms(rooms2);
        System.out.println("result2 = " + result2);//true

    }

}
