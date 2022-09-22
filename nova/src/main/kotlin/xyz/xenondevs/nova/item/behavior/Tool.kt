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
import xyz.xenondevs.nova.material.options.DamageOptions
import xyz.xenondevs.nova.material.options.ToolOptions
import xyz.xenondevs.nova.util.data.appendLocalized
import xyz.xenondevs.nova.util.data.localized
import xyz.xenondevs.nova.util.nmsStack
import kotlin.math.roundToInt

private const val PLAYER_ATTACK_SPEED = 4.0
private const val PLAYER_ATTACK_DAMAGE = 1.0

class Tool(val toolOptions: ToolOptions, val damageOptions: DamageOptions?) : ItemBehavior() {
    
    override val vanillaMaterialProperties = listOf(VanillaMaterialProperty.DAMAGING_NORMAL)
    
    override val attributeModifiers = buildList {
        if (damageOptions != null) {
            this += AttributeModifier(
                Attribute.GENERIC_ATTACK_SPEED,
                AttributeModifier.Operation.INCREMENT,
                damageOptions.attackSpeed - PLAYER_ATTACK_SPEED,
                EquipmentSlot.MAINHAND
            )
            
            this += AttributeModifier(
                Attribute.GENERIC_ATTACK_DAMAGE,
                AttributeModifier.Operation.INCREMENT,
                damageOptions.attackDamage - PLAYER_ATTACK_DAMAGE,
                EquipmentSlot.MAINHAND
            )
        }
    }
    
    override fun updatePacketItemData(itemStack: ItemStack, itemData: PacketItemData) {
        if (damageOptions != null) {
            itemData.addLore(arrayOf(TextComponent(" ")))
            itemData.addLore(arrayOf(localized(ChatColor.GRAY, "item.modifiers.mainhand")))
            
            val mojangStack = itemStack.nmsStack
            val attackDamage = (damageOptions.attackDamage + EnchantmentHelper.getDamageBonus(mojangStack, MobType.UNDEFINED)).roundToInt()
            itemData.addLore(ComponentBuilder(" $attackDamage ")
                .color(ChatColor.DARK_GREEN)
                .appendLocalized("attribute.name.generic.attack_damage")
                .create())
            itemData.addLore(ComponentBuilder(" ${damageOptions.attackSpeed} ")
                .color(ChatColor.DARK_GREEN)
                .appendLocalized("attribute.name.generic.attack_speed")
                .create())
        }
        
        itemData.hide(HideableFlag.MODIFIERS)
    }
    
}