package net.aaronterry.hisb.exploration.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.aaronterry.hisb.HisbMod;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BookScrapScreen extends HandledScreen<BookScrapScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(HisbMod.id(), "textures/gui/book_scrap.png");

    public BookScrapScreen(BookScrapScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawTextWrapped(textRenderer, StringVisitable.plain(handler.getScrapText()), backgroundWidth / 3, backgroundHeight / 3, 2 * backgroundWidth / 3, 0xFFFFFF);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    protected void init() {
        super.init();
        titleY = 1000;
    }


}
