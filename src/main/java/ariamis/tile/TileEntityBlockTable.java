package ariamis.tile;

import ariamis.blocks.BlockTable;
import ariamis.help.ComparableItemStack;
import ariamis.help.ComparableMap;
import ariamis.help.Pair;
import ariamis.help.TableRecipes;
import ariamis.items.ItemRegistry;
import ariamis.tile.container.TableContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.*;

/**
 * Created by detro on 14.04.2020.
 */
public class TileEntityBlockTable extends TileEntity implements ISidedInventory {
    public TileEntityBlockTable master;
    private boolean isMaster;
    public int rotation;



    //   must be at least one      water is required   must have at least number of burning items or items || with input recipe
    //   [ normal iventory part ] [ consumable items ] [output slots]
    private ItemStack[] inventory = new ItemStack[16];
    private int ticksSinceSync;
    private String customName;
    public Random random;
    public int burningTime;
    public int currentItemBurnTime;
    public int furnaceCookTime;

    public int numPlayersUsing;

    public TileEntityBlockTable(){
        onMasterNull();
    }

    private void rotation(int x, int y, int z){
        if(isMaster())
            if(rotation!=0){
                Pair<Integer, Integer> p = getPosFromState(rotation);
                if(!(worldObj.getTileEntity(xCoord+p.getX(),yCoord,zCoord+p.getY()) instanceof TileEntityBlockTable))
                    rotation=getStateFromPair(x-xCoord,z-zCoord);
            }
            else rotation=getStateFromPair(x-xCoord,z-zCoord);
    }

    public void findMaster(){
        for(int i=0;i<4;i++){
            Pair<Integer,Integer> pair = getPosFromState(i+1);
            if(worldObj!=null){
            try {
                TileEntity te = worldObj.getTileEntity(xCoord + pair.getX(), yCoord, zCoord + pair.getY());
                if (te instanceof TileEntityBlockTable) {
                    if ( !((TileEntityBlockTable) te).isMaster() && ((TileEntityBlockTable) te).master == null) {
                        ((TileEntityBlockTable) te).isMaster = true;

                        ((TileEntityBlockTable) te).rotation(xCoord, yCoord, zCoord);
                        this.master = ((TileEntityBlockTable) te);
                        this.isMaster = false;
                        System.out.println("Master set"+xCoord+" "+yCoord+" "+zCoord+" angle is: "+((TileEntityBlockTable) te).rotation);
                        Minecraft.getMinecraft().theWorld.setTileEntity(xCoord, yCoord, zCoord, this);
                        Minecraft.getMinecraft().theWorld.setTileEntity(xCoord + pair.getX(), yCoord, zCoord + pair.getY(), te);
                        Minecraft.getMinecraft().theWorld.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord,xCoord + pair.getX(), yCoord, zCoord + pair.getY());

                        return;
                    }
                }
            }catch (NullPointerException e){
                //System.out.println("NPE not founded entity");
            }
            }
        }
        onTEDestroyed();

    }

    public void onTEDestroyed(){
        this.master=null;
        this.isMaster=false;
    }

    public TileEntityBlockTable getMaster(){
        return isMaster?this:master;
    }

    public void onMasterNull(){
        if(master==null && !isMaster){
            findMaster();
        }
    }


    public int sideToClassicInverted(int side){
        switch (side){
            case 1:return 4;
            case 2:return 2;
            case 3:return 5;
            case 4:return 3;
            default:return 0;
        }
    }


    private static int getStateFromPair(int a, int b){
        if(a==b || a==-b)return 0;
        if(a==1)
            return 1;
        if(a==-1)
            return 2;
        if(b==1)
            return 3;
        if(b==-1)
            return 4;
        return 0;
    }

    private static Pair<Integer, Integer> getPosFromState(int pos){
        if(pos<=0 || pos>=6)return  new Pair<Integer, Integer>(0,0);
        pos-=1;
        int t = pos % 2;
        int s = (pos / 2) % 2;
        int sx = t == 0 ? (s == 0 ? + 1 : - 1) : 0;
        int sz = t == 1 ? (s == 0 ? + 1 : - 1) : 0;

        return new Pair<Integer, Integer>(sx,sz);
    }


    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        NBTTagList nbttaglist = p_145839_1_.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.inventory.length) {
                this.inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.burningTime = p_145839_1_.getShort("BurnTime");
        this.furnaceCookTime = p_145839_1_.getShort("CookTime");
        this.currentItemBurnTime = getItemBurnTime(this.inventory[1]);
    }
    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setShort("BurnTime", (short)this.burningTime);
        p_145841_1_.setShort("CookTime", (short)this.furnaceCookTime);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.inventory.length; ++i) {
            if (this.inventory[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        p_145841_1_.setTag("Items", nbttaglist);
    }

    public boolean isMaster() {
        return isMaster;
    }




    @Override
    public void updateEntity() {
        super.updateEntity();
        ++this.ticksSinceSync;
        float f;
        // synchronising
        if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0) {
            this.numPlayersUsing = 0;
            f = 5.0F;
            List list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox((double) ((float) this.xCoord - f), (float) this.yCoord - f, (double) ((float) this.zCoord - f), (double) ((float) (this.xCoord + 1) + f), (double) ((float) (this.yCoord + 1) + f), (double) ((float) (this.zCoord + 1) + f)));
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer) iterator.next();
                if (entityplayer.openContainer instanceof TableContainer) {
                    IInventory iinventory = ((TableContainer) entityplayer.openContainer).getTableInventory();
                    if (iinventory == this || iinventory instanceof TableContainer) ++this.numPlayersUsing;
                }
            }
        }
        if(!isMaster)return;
        boolean flag = this.burningTime > 0;
        boolean flag1 = false;


        //update fuel consuming
        if (this.burningTime > 0) --this.burningTime;

        if (!this.worldObj.isRemote) {
            if (this.burningTime != 0 || hasFuel() && hasRecipe() && hasWater()) {
                if (this.burningTime == 0 && hasWater() && this.getSmeltingItems().length>0) {
                    this.currentItemBurnTime = this.burningTime = getItemBurnTime(inventory[2]);
                    if (this.burningTime > 0) {
                        flag1 = true;

                        if (this.inventory[0] != null) {
                            --this.inventory[0].stackSize;

                            if (this.inventory[0].stackSize == 0) {
                                this.inventory[0] = inventory[0].getItem().getContainerItem(inventory[0]);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.getSmeltingItems().length>0) {
                    ++this.furnaceCookTime;

                    if (this.furnaceCookTime == 200) {
                        this.furnaceCookTime = 0;
                        this.smeltItem();
                        flag1 = true;
                    }
                } else {
                    this.furnaceCookTime = 0;
                }
            }

            if (flag != this.burningTime > 0) {
                flag1 = true;
                //можно обновить анимацию
                //BlockFurnace.updateFurnaceBlockState(this.burningTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }

        if (flag1) {
            this.markDirty();
        }


    }
    public void closeInventory() {
        if (this.getBlockType() instanceof BlockTable) {
            --this.numPlayersUsing;
            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
        }
    }
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return slot<2+4;
    }
    public int getSizeInventory() {
        return inventory.length;
    }

    public ItemStack getStackInSlot(int slot) {
        if (slot < 15) return inventory[slot];
        else return null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int number) {
        if (this.inventory[slot] != null) {
            ItemStack itemstack;
            if (this.inventory[slot].stackSize <= number) {
                itemstack = this.inventory[slot];
                this.inventory[slot] = null;
                this.markDirty();
                return itemstack;
            } else {
                itemstack = this.inventory[slot].splitStack(number);
                if (this.inventory[slot].stackSize == 0)
                    this.inventory[slot] = null;
                this.markDirty();
                return itemstack;
            }
        } else return null;
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.inventory[slot] != null) {
            ItemStack itemstack = this.inventory[slot];
            this.inventory[slot] = null;
            return itemstack;
        } else return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        this.inventory[slot] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
            stack.stackSize = this.getInventoryStackLimit();
        this.markDirty();

    }

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.customName : "container.chest";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.customName != null && this.customName.length() > 0;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {
        if (this.numPlayersUsing < 0) this.numPlayersUsing = 0;
        ++this.numPlayersUsing;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
    }

    private void smeltItem() {
        ComparableItemStack[] smelting= getSmeltingItems();
        if (smelting.length>0) {
            if(random==null)random=new Random(worldObj.getWorldTime());
            else random.setSeed(worldObj.getWorldTime());


            ComparableItemStack[] result = TableRecipes.tableRecipes().availableResults(smelting);
            ComparableMap result_probability = ComparableMap.normalisation(TableRecipes.tableRecipes().getResults(smelting).revSortByValues().splice(0, 3));
            ComparableItemStack[] output_slots = ComparableItemStack.fromItemArray(splice(inventory, 6, 9));
            int count_avaliable = 9;
            for (int i = 0; i < 9; i++)
                if (inventory[6+i] != null) count_avaliable--;
            int count_intersection = ComparableItemStack.intersection(output_slots, result);

            // при создании рецепта изначально определяется сколько эффектов будет добавлено к зелью
            // изначально нормализуются вероятности и фиксируются их значения создается массив с возрастающими числами которые являются максимальными порогами для добавления эффекта
            // далее это действие повторяется n раз
            int slotDecrease = 2;
            if (count_avaliable > 3 || (count_intersection == 9 - count_avaliable)) {

                for (int i = 0; i < smelting.length; i++) {
                    double probability = random.nextDouble();
                    int number_of_effects = 0;  // num
                    if (probability > 0.2) {
                        if (probability < 0.6) number_of_effects = 1;
                        else if (probability < 0.85) number_of_effects = 2;
                        else number_of_effects = 3;
                    }
                    Integer[] ids = (Integer[]) result_probability.keyArray(new Integer[0]);
                    Float[] probabilities = (Float[]) result_probability.entryArray(new Float[0]);
                    int meta = 0;
                    if (number_of_effects == 3) meta = (1 << ids[0]) + (1 << ids[1]) + (1 << ids[2]);
                    else if (number_of_effects == 0) meta = 0;
                    else for (int t = 0; t < number_of_effects; t++) {

                            double counter;
                            do{
                                probability = random.nextDouble();
                                counter=0;
                                for (int j = 0; j < 3; j++) {
                                    counter += probabilities[j];
                                    if (probability < counter) {
                                        if (meta != 1 << ids[j]) meta += 1 << ids[j];
                                        else counter = -1;

                                        break;
                                    }
                                }
                            }while(counter==-1);
                        }
                    int slot = 0;
                    boolean fill = false;
                    if (count_avaliable==9 || output_slots.length==0) {
                        fill = true;
                        for (int k = 0; k < 9; k++) {
                            if (inventory[6+k] == null){
                                slot = k;
                                break;
                            }
                        }
                    }
                    else {
                        int slot_test;
                        slot_test= ComparableItemStack.arraySearch(output_slots, new ComparableItemStack(ItemRegistry.itemdust, 1, meta));
                        if(slot_test==-1){
                            fill = true;
                            for (int k = 0; k < 9; k++) {
                                if (inventory[6+k] == null){
                                    slot = k;
                                    break;
                                }
                            }
                        }
                        else slot=slot_test;
                    }

                    if (fill || inventory[6+slot]==null) inventory[6+slot] = new ItemStack(ItemRegistry.itemdust, 1, meta);
                    else if(inventory[6+slot]!=null)inventory[6+slot].stackSize++;

                    do{slotDecrease = slotDecrease+1>5?2:slotDecrease+1;}
                    while (inventory[slotDecrease] == null ||  !canSmelt(new ComparableItemStack(inventory[slotDecrease].getItem(),1,inventory[slotDecrease].getItemDamage())));

                    --this.inventory[slotDecrease].stackSize;
                    if (this.inventory[slotDecrease].stackSize <= 0)
                        this.inventory[slotDecrease] = null;

                    output_slots= ComparableItemStack.fromItemArray(splice(inventory, 6, 9));
                    count_avaliable=9;
                    for (int t = 0; t < 9; t++)
                        if (inventory[6+t] != null) count_avaliable--;
                    count_intersection = ComparableItemStack.intersection(output_slots, result);
                }
            }
        }
    }


    private int getItemBurnTime(ItemStack is) {
        if (is == null)return 0;
        if (is.getItem() == Items.blaze_rod)
            return 2400;
        if (is.getItem() == Items.blaze_powder)
            return 1000;
        else return 0;
    }


    private boolean isBurning() {
        return this.burningTime > 0;
    }

    private boolean hasFuel() {
        return inventory[0] != null;
    }

    private boolean hasWater() {
        return inventory[1] != null;
    }

    private boolean hasRecipe() {
        return  canSmelt(inventory[2]) || canSmelt(inventory[3]) || canSmelt(inventory[4]) || canSmelt(inventory[5]);
    }
    private boolean canSmelt(ComparableItemStack itemStack) {
        return TableRecipes.match(itemStack);
    }
    private boolean canSmelt(ItemStack itemStack) {
        if(itemStack!=null)
            return TableRecipes.match(new ComparableItemStack(itemStack.getItem(),1,itemStack.getItemDamage()));
        else return false;
    }

    private ComparableItemStack[] getSmeltingItems(){
        List sm =new ArrayList();
        for(ItemStack o: splice(inventory, 2, 4))
            if(o!=null && canSmelt(new ComparableItemStack(o.getItem(),1,o.getItemDamage())))
                sm.add(new ComparableItemStack(o.getItem(),1,o.getItemDamage()));
        return (ComparableItemStack[])sm.toArray(new ComparableItemStack[0]);
    }

    //return linked items
    private ItemStack[] splice(ItemStack[] array, int from) {
        return splice(array, from, array.length - from);
    }

    //return linked items
    private ItemStack[] splice(ItemStack[] array, int from, int num) {
        ItemStack[] val = new ItemStack[num];
        for (int i = 0; i < num; i++)
            val[i] = array[from + i];
        return val;
    }



    /**
     * Returns an array containing the indices of the slots that can be accessed by automation on the given side of this
     * block.
     */
    public int[] getAccessibleSlotsFromSide(int side) {
        int[] a;
        switch(side){
            case 0:
                a=new int[9];
                for(int i=0;i<9;i++)a[i]=i+6;
                return a;
            case 1:
                return new int[]{2,3,4,5};
            default:
                return new int[]{0, 1};

        }
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
        return this.isItemValidForSlot(p_102007_1_, p_102007_2_);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return side != 0 || slot != 1 || stack.getItem() == Items.bucket;
    }
}
