package client.gui;

import org.lwjgl.opengl.GL11;

import client.gui.container.ContainerSwordOfDemon;
import common.SimplyGenerators;
import common.item.tool.ItemSwordOfDemon;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import util.MessageMarks;

public class GuiSwordOfDemon extends GuiContainer{

	private static final ResourceLocation TEXTURE = new ResourceLocation("simplygenerators", "textures/gui/ArmorOfDemon.png");

	private String text;
	private GuiTextField fieldNamed;

	private ContainerSwordOfDemon contain;

	private EntityPlayer player;
	private ItemStack stack;
	private ItemSwordOfDemon demonSword;

	private int guiUpdateTick;

    public GuiSwordOfDemon(int x, int y, int z) {
        super(new ContainerSwordOfDemon(x, y, z));

        this.xSize = 176;
        this.ySize = 166;
    }

    @SuppressWarnings("unchecked")
	@Override
	public void initGui(){
		super.initGui();
		buttonList.clear();

		buttonList.add(new GuiButton(0, this.guiLeft + 36, this.guiTop + 132, 100, 20, "Cancel"));//close_inventory);
		buttonList.add(new GuiButton(1, this.guiLeft + 36, this.guiTop + 70, 100, 20, "Change Name"));//sumbit

		this.fieldNamed = new GuiTextField(this.fontRendererObj, this.guiLeft + 36, this.guiTop + 35, 100, 20);
		this.fieldNamed.setFocused(true);
		this.fieldNamed.setMaxStringLength(40);
    }

    /*GUIの文字等の描画処理*/
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ){

    	this.fontRendererObj.drawString(
				StatCollector.translateToLocal("Customize"), this.xSize / 3 + 5, 6, 0x000000);

        super.drawGuiContainerForegroundLayer(mouseX, mouseZ);
    }

    /*GUIの背景の描画処理*/
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseZ){
    	GL11.glColor4f(1F, 1F, 1F, 1F);

    	drawDefaultBackground();

    	this.mc.renderEngine.bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);

        this.fieldNamed.drawTextBox();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public void keyTyped(char c, int i){
    	super.keyTyped(c, i);

    	if(this.fieldNamed.isFocused()){
    		this.fieldNamed.textboxKeyTyped(c, i);
    	}
    	if(i == 28){
    		this.submitButton();
    	}
    }

    public void mouseClickd(int i, int j, int k){
    	super.mouseClicked(i, j, k);
    	this.fieldNamed.mouseClicked(i, j, k);
    }

    @Override
	protected void actionPerformed(GuiButton button){
		if(button.id == 0){
			this.mc.thePlayer.closeScreen();
		}else if(button.id == 1){
			this.submitButton();
		}
    }

    public void submitButton() {
		this.mc.thePlayer.closeScreen();
		if (this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSwordOfDemon) {
			//send pack to server with name.
			MessageMarks msg = new MessageMarks(this.fieldNamed.getText());
			SimplyGenerators.network.sendToServer(msg);
		}
	}

    /*@Override
	public void updateScreen(){
		guiUpdateTick++;
		if(this.guiUpdateTick >= 10){
			this.initGui();
			this.guiUpdateTick = 0;
		}

		fieldNamed.updateCursorCounter();

		super.updateScreen();
	}*/

	@Override
	public void onGuiClosed(){
		super.onGuiClosed();
		//Keyboard.enableRepeatEvents(false);

		//player.inventory.getCurrentItem().setStackDisplayName(text);
	}
}
