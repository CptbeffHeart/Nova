package xyz.xenondevs.nova.item.behavior

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.TextComponent
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.MobType
import net.minecraft.world.item.enchantment.EnchantmentHelper
import org.bukkit.attribute.Attribute
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.nova.item.PacketItemData
import xyz.xenondevs.nova.item.vanilla.AttributeModifier
import xyz.xenondevs.nova.item.vanilla.HideableFlag
import xyz.xenondevs.nova.item.vanilla.VanillaMaterialProperty
import xyz.xenondevs.nova.material.ItemNovaMaterial
import xyz.xenondevs.nova.material.options.ToolOptions
import xyz.xenondevs.nova.util.data.appendLocalized
import xyz.xenondevs.nova.util.data.localized
import xyz.xenondevs.nova.util.nmsStack
import kotlin.math.roundToInt

private const val PLAYER_ATTACK_SPEED = 4.0
private const val PLAYER_ATTACK_DAMAGE = 1.0

class Tool(val options: ToolOptions) : ItemBehavior() {
    
    override val vanillaMaterialProperties = buildList {
        this += VanillaMaterialProperty.DAMAGEABLE
        if (!options.canBreakBlocksInCreative)
            this += VanillaMaterialProperty.CREATIVE_NON_BLOCK_BREAKING
    }
    
    override val attributeModifiers = buildList {
        if (options.attackSpeed != null) {
            this += AttributeModifier(
                Attribute.GENERIC_ATTACK_SPEED,
                AttributeModifier.Operation.INCREMENT,
                options.attackSpeed!! - PLAYER_ATTACK_SPEED,
                EquipmentSlot.MAINHAND
            )
        }
        if (options.attackDamage != null) {
            this += AttributeModifier(
                Attribute.GENERIC_ATTACK_DAMAGE,
                AttributeModifier.Operation.INCREMENT,
                options.attackDamage!! - PLAYER_ATTACK_DAMAGE,
                EquipmentSlot.MAINHAND
            )
        }
    }
    
    override fun updatePacketItemData(itemStack: ItemStack, itemData: PacketItemData) {
        if (options.attackDamage != null && options.attackSpeed != null) {
            itemData.addLore(arrayOf(TextComponent(" ")))
            itemData.addLore(arrayOf(localized(ChatColor.GRAY, "item.modifiers.mainhand")))
            
            val mojangStack = itemStack.nmsStack
            val attackDamage = (options.attackDamage!! + EnchantmentHelper.getDamageBonus(mojangStack, MobType.UNDEFINED)).roundToInt()
            itemData.addLore(ComponentBuilder(" $attackDamage ")
                .color(ChatColor.DARK_GREEN)
                .appendLocalized("attribute.name.generic.attack_damage")
                .create())
            itemData.addLore(ComponentBuilder(" ${options.attackSpeed} ")
                .color(ChatColor.DARK_GREEN)
                .appendLocalized("attribute.name.generic.attack_speed")
                .create())
        }
        
        itemData.hide(HideableFlag.MODIFIERS)
    }
    
    companion object : ItemBehaviorFactory<Tool>() {
        override fun create(material: ItemNovaMaterial) =
            Tool(ToolOptions.configurable(material))
    }
    
}