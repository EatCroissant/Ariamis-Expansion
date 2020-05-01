package ariamis.help;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class ComparableItemStack implements Comparable {
    private Item item;
    private int meta;
    public ComparableItemStack(Block block) {
        this(block, 0, 0);
    }

    public ComparableItemStack(Block block, int stack) {
         this(block, stack, 0);
    }

    public ComparableItemStack(Block block, int stack, int meta) {
        this(Item.getItemFromBlock(block), stack, meta);
    }

    public ComparableItemStack(Item item) {
        this(item, 0, 0);
    }

    public ComparableItemStack(Item item, int stack) {
        this(item, stack, 0);
    }

    public ComparableItemStack(Item item, int stack, int meta) {
        this.item=item;
        this.meta=meta;
    }



    public static ComparableItemStack[] fromItemArray(ItemStack[] items){
        int notNull=0;
        for(int i = 0; i < items.length;i++)
            if(items[i] != null && items[i].getItem()!=null )notNull++;
        ComparableItemStack[] comparableItemStack = new ComparableItemStack[notNull];
        for(int i = 0; i < items.length;i++){
            if(items[i] != null && comparableItemStack.length>0 ) {
                comparableItemStack[notNull - 1] = new ComparableItemStack(items[i].getItem(), items[i].stackSize, items[i].getItemDamage());
                notNull--;
            }
        }
        return comparableItemStack;
    }

    public int getMeta() {
        return meta;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public int compareTo(Object  o) {
        if(o==null)return 1;
        if(o instanceof ComparableItemStack && this.item!=null && this.item.equals( ((ComparableItemStack)o).getItem() ) && this.meta==((ComparableItemStack)o).getMeta()) return 0;
        else return -1;
    }

    public static int arraySearch(ComparableItemStack[] stack, ComparableItemStack needle){
        for(int i=0;i<stack.length;i++)
            if(needle!=null && needle.compareTo(stack[i])==0)
                return i;

        return -1;
    }
    public static int arraySearch(ComparableItemStack[] stack, ComparableItemStack needle, int end){
        for(int i=0;i<Math.min(stack.length,end);i++)
            if(needle!=null && needle.compareTo(stack[i])==0)
                return i;

        return -1;
    }

    public static int intersection(ComparableItemStack[] stack1, ComparableItemStack[] stack2){
        int count=0;
        boolean[] b = new boolean[stack1.length];
        Arrays.fill(b,false);
        for(int i=0;i<stack1.length;i++)
            if(arraySearch(stack2, stack1[i])!=-1 && -1==arraySearch(stack1,stack1[i],i)){
                count++;
            }

        return count;
    }

}
