package io.neow3j.examples.contractdevelopment.contracts;

import java.nio.ByteBuffer;

import io.neow3j.devpack.ByteString;
import io.neow3j.devpack.constants.CallFlags;
import io.neow3j.protocol.core.stackitem.StackItem;
import io.neow3j.wallet.Account;
import io.neow3j.devpack.Contract;
import io.neow3j.devpack.Hash160;
import io.neow3j.devpack.Storage;
import io.neow3j.devpack.StorageContext;
import io.neow3j.devpack.StorageMap;
import io.neow3j.devpack.Runtime;
import io.neow3j.devpack.annotations.ManifestExtra;
import io.neow3j.devpack.annotations.OnDeployment;
import io.neow3j.devpack.annotations.Permission;
import io.neow3j.devpack.annotations.Safe;

@ManifestExtra(key = "author", value = "Colin")
@Permission(contract = "*", methods = "*") 
public class TicTacToe {
    static final ByteString P1KEY = new ByteString("P1KEY");
    static final ByteString P2KEY = new ByteString("P1KEY");
    static final ByteString LASTPLAYERKEY = new ByteString("LASTPLAYERKEY");
    static final ByteString WINNERKEY = new ByteString("WINNERKEY");
    
    static final ByteString GAMESTATUSKEY = new ByteString("GAMESTATUSKEY"); //0 = still going. // 1 = over

    static final StorageContext ctx = Storage.getStorageContext();
    static final StorageMap s_playersMap = ctx.createMap((byte) 4);
    static final StorageMap s_gameStatusMap = ctx.createMap((byte) 1);
    static final StorageMap s_gameBoardMap = ctx.createMap((byte) 9);
    /** The game board itself
     * 0, 1, 2
     * 3, 4, 5
     * 6, 7, 8
     */

    @OnDeployment
    public static void deploy(Object data, boolean update) throws Exception {
        if (!update) {
            for (int i = 0; i < 10; i++) {
                setGamePositionValue(i, 0);
            }
        } else {
            throw new Exception("Could not initialize.");
        }
    }
    
    // MAP UTILS
    static public void setPlayer(Hash160 _player, ByteString _key) {
        s_playersMap.put(_key, _player);
    }

    static public Hash160 getPlayer(ByteString _key) {
        return new Hash160(s_playersMap.get(_key));
    }

    static public void setGameStatus(int _status) {
        s_gameStatusMap.put(GAMESTATUSKEY, _status);
    }

    static public int getGameStatus() {
        return s_gameStatusMap.getInteger(GAMESTATUSKEY);
    }

    static public void setGamePositionValue(int _location, int _value) {
        s_gameBoardMap.put(new byte[_location], _value);
    }

    static public int getGamePositionValue(int _location) {
        return s_gameBoardMap.getInteger(new byte[_location]);
    }

    // GETTERS
    @Safe 
    public static int[] getBoard() {
        int[] board = new int[10];
        for (int i = 0; i < 10; i++) {
            board[i] = getGamePositionValue(i);
        }
        return board;
    }

    @Safe
    public static Hash160[] getPlayers() {
        return new Hash160[]{getPlayer(P1KEY), getPlayer(P2KEY)};
    }

    @Safe
    public static Hash160 getWinner() {
        return getPlayer(WINNERKEY);
    }

    @Safe
    public static Hash160 getLastPlayed() {
        return getPlayer(LASTPLAYERKEY);
    }

    @Safe
    public static boolean isGameOver() {
        return  (boolean) (getGameStatus() == 1);
    }

    // METHODS
    public static void startGame(Hash160 _player1, Hash160 _player2) throws Exception {
        setPlayer(_player1, P1KEY);
        setPlayer(_player2, P2KEY);
    }

    public static void takeTurn(int _boardLocation, Hash160 _player) throws Exception {
        

        if (_player.equals(getPlayer(P1KEY))) {
            setGamePositionValue(_boardLocation, 1);
        } else {
            setGamePositionValue(_boardLocation, 2);
        }

        setPlayer(_player, LASTPLAYERKEY);;
    }    
}