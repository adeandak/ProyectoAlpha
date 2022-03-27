package Interfaz;

import java.util.Hashtable;
import java.util.Map;


public class HashPlayer extends Hashtable<String,Integer> {

    private Integer maxValue = Integer.MIN_VALUE;   // Inicializamos el valor máximo con el más chico
    private String maxKey = "";

    public String getMaxKey() {
        return maxKey;
    }

    @Override
    public synchronized Integer put(String key, Integer value) {
        maxValue = Math.max(maxValue, value);

        if(maxValue == value)
            maxKey = key;

        return super.put(key, value);
    }

    @Override
    public synchronized void clear() {
        super.clear();
        maxValue = Integer.MIN_VALUE;   // Reiniciamos el valor máximo con el más pequeño
    }

    public Integer getMaxValue() {
        return this.maxValue;
    }

    @Override
    public synchronized Integer remove(Object key) {
        Map.Entry<String, Integer> maxEntry = null;
        Integer res = super.remove(key);
        if(!this.isEmpty())
        {
            for (Map.Entry<String, Integer> entry : this.entrySet())
            {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
                {
                    maxEntry = entry;
                }
            }
            this.maxKey = maxEntry.getKey();
            this.maxValue = maxEntry.getValue();
        }
        return res;
    }

}
