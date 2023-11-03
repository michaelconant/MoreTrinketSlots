package moretrinketslots.modclasses.items;

public class TrinketSlotsItemConfig implements Comparable<TrinketSlotsItemConfig> {
    public String itemID;
    public int minSlots;
    public int maxSlots;
    public int increment;
    public float brokerValue;
    public TrinketSlotsItemConfig(String itemID, int min, int max, int inc, float brokerValue) {
        this.itemID = itemID;
        this.minSlots = min;
        this.maxSlots = max;
        this.increment = inc;
        this.brokerValue = brokerValue;
    }

    public TrinketSlotsItemConfig(String itemID, int min, int max, int inc) {
        this.itemID = itemID;
        this.minSlots = min;
        this.maxSlots = max;
        this.increment = inc;
        this.brokerValue = 100.0F;
    }

    public TrinketSlotsItemConfig(TrinketSlotsItemConfig config) {
        this.itemID = config.itemID;
        this.minSlots = config.minSlots;
        this.maxSlots = config.maxSlots;
        this.increment = config.increment;
        this.brokerValue = config.brokerValue;
    }

    @Override
    public int compareTo(TrinketSlotsItemConfig itemConfig) {
        //compare by min slots
        if (this.minSlots > itemConfig.minSlots) {
            return +1;
        } else if (this.minSlots < itemConfig.minSlots) {
            return -1;
        } else {
            //compare by max slots
            if (this.maxSlots < itemConfig.maxSlots) {
                return +1;
            } else if (this.maxSlots > itemConfig.maxSlots) {
                return -1;
            } else {
                //compare by increment
                return Integer.compare(this.increment, itemConfig.increment);
            }
        }
    }
}