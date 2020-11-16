// julian saldana 018462169

package lab3;

import java.lang.reflect.Array;

public class HashSet<T> {
    private Entry[] mTable;
    private int mCount;

    public void HashMap(int tableSize){
        int num = 1;
        while (num < tableSize){
            num *= 2;
        }

        mTable = (Entry[])Array.newInstance(Entry.class, num);
    }

    private class Entry {
        public T mKey;
        public boolean mIsNil;

        public Entry(T key, boolean nilCheck){
            mKey = key;
            mIsNil = nilCheck;
        }
    }

    public void add(T key){
        if (loadFactor() > 0.8)
            newHash();

        int hashFunc = Math.abs(key.hashCode());
        Entry tempEntry = new Entry(key, false);

        for (int i = 0; i < mTable.length; i++) {
            int index = (hashFunc + probingFunction(i)) % mTable.length;
            if (mTable[index] == null || mTable[index].mIsNil) {
                mTable[index] = tempEntry;
                mCount++;
                break;
            } else if (mTable[index].mKey.equals(key))
                break;
        }
    }

    public void remove(T key) {
        int index = 0;
        int hashCode = Math.abs(key.hashCode());
        int position = (hashCode + probingFunction(index)) % mTable.length;

        while (mTable[position] != null) {
            if (mTable[position].mKey.equals(key)){
                mTable[position].mKey = null;
                mTable[position].mIsNil = true;
                mCount = mCount - 1;
            } else if (index >= mTable.length){
                break;
            }
            index++;
            position = (hashCode + probingFunction(index)) % mTable.length;
        }
    }

    public boolean find(T key){
        int index = 0;
        int hashCode = Math.abs(key.hashCode());
        int position = hashCode + probingFunction(index) % mTable.length;

        while (mTable[position] != null){
            if (!mTable[position].mIsNil && mTable[position].mKey.equals(key))
                return true;
            else if (index >= mTable.length)
                return false;

            index++;
            position = (hashCode + probingFunction(index)) % mTable.length;
        }
        return false;
    }

    public int count(){
       return mCount;
    }

    public double loadFactor(){
        return (mCount / mTable.length);
    }

    int probingFunction(int index){
       return ((index*index + index) / 2);
    }

    public void newHash(){
        Entry[] table = mTable;
        HashMap(mTable.length * 2);
        mCount = 0;

        for (Entry entry : table) {
            if (entry != null && !entry.mIsNil) {
                add(entry.mKey);
            }
        }
    }
}