package xyz.xenondevs.nova.util.reflection

import com.mojang.brigadier.tree.CommandNode
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.chunk.HashMapPalette
import net.minecraft.world.level.chunk.LinearPalette
import net.minecraft.world.level.chunk.PalettedContainer
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.MemorySection
import org.bukkit.craftbukkit.v1_19_R1.block.CraftBlock
import org.bukkit.event.HandlerList
import org.bukkit.event.block.BlockPhysicsEvent
import org.bukkit.event.inventory.PrepareItemCraftEvent
import xyz.xenondevs.nova.util.reflection.ReflectionUtils.getCB
import xyz.xenondevs.nova.util.reflection.ReflectionUtils.getCBClass
import xyz.xenondevs.nova.util.reflection.ReflectionUtils.getClass
import xyz.xenondevs.nova.util.reflection.ReflectionUtils.getConstructor
import xyz.xenondevs.nova.util.reflection.ReflectionUtils.getField
import xyz.xenondevs.nova.util.reflection.ReflectionUtils.getMethod
import java.util.*
import java.util.function.Consumer
import kotlin.jvm.internal.CallableReference
import kotlin.reflect.KProperty1
import net.minecraft.world.item.ItemStack as MojangStack

@Suppress("MemberVisibilityCanBePrivate")
internal object ReflectionRegistry {
    
    // Paths
    val CB_PACKAGE_PATH = getCB()
    
    // Classes
    val CB_CRAFT_META_ITEM_CLASS = getCBClass("inventory.CraftMetaItem")
    val SECTION_PATH_DATA_CLASS = getClass("org.bukkit.configuration.SectionPathData")
    val PALETTED_CONTAINER_DATA_CLASS = getClass("SRC(net.minecraft.world.level.chunk.PalettedContainer\$Data)")
    
    // Constructors
    val ENUM_MAP_CONSTRUCTOR = getConstructor(EnumMap::class.java, false, Class::class.java)
    val MEMORY_SECTION_CONSTRUCTOR = getConstructor(MemorySection::class.java, true, ConfigurationSection::class.java, String::class.java)
    val SECTION_PATH_DATA_CONSTRUCTOR = getConstructor(SECTION_PATH_DATA_CLASS, true, Any::class.java)
    
    // Methods
    val CB_CRAFT_META_APPLY_TO_METHOD = getMethod(CB_CRAFT_META_ITEM_CLASS, true, "applyToItem", CompoundTag::class.java)
    val K_PROPERTY_1_GET_DELEGATE_METHOD = getMethod(KProperty1::class.java, false, "getDelegate", Any::class.java)
    val CRAFT_BLOCK_IS_PREFERRED_TOOL_METHOD = getMethod(CraftBlock::class.java, true, "isPreferredTool", BlockState::class.java, MojangStack::class.java)
    val ITEM_STACK_GET_ATTRIBUTE_MODIFIERS_METHOD = getMethod(MojangStack::class.java, false, "SRM(net.minecraft.world.item.ItemStack getAttributeModifiers)", EquipmentSlot::class.java)
    val ITEM_STACK_HURT_AND_BREAK_METHOD = getMethod(MojangStack::class.java, false, "SRM(net.minecraft.world.item.ItemStack hurtAndBreak)", Int::class.java, LivingEntity::class.java, Consumer::class.java)
    val ITEM_STACK_HURT_ENTITY_METHOD = getMethod(MojangStack::class.java, false, "SRM(net.minecraft.world.item.ItemStack hurtEnemy)", LivingEntity::class.java, Player::class.java)
    
    // Fields
    val CRAFT_META_ITEM_UNHANDLED_TAGS_FIELD = getField(CB_CRAFT_META_ITEM_CLASS, true, "unhandledTags")
    val COMMAND_NODE_CHILDREN_FIELD = getField(CommandNode::class.java, true, "children")
    val COMMAND_NODE_LITERALS_FIELD = getField(CommandNode::class.java, true, "literals")
    val COMMAND_NODE_ARGUMENTS_FIELD = getField(CommandNode::class.java, true, "arguments")
    val CALLABLE_REFERENCE_RECEIVER_FIELD = getField(CallableReference::class.java, true, "receiver")
    val PREPARE_ITEM_CRAFT_EVENT_MATRIX_FIELD = getField(PrepareItemCraftEvent::class.java, true, "matrix")
    val MEMORY_SECTION_MAP_FIELD = getField(MemorySection::class.java, true, "map")
    val HANDLER_LIST_HANDLERS_FIELD = getField(HandlerList::class.java, true, "handlers")
    val HANDLER_LIST_HANDLER_SLOTS_FIELD = getField(HandlerList::class.java, true, "handlerslots")
    val BLOCK_PHYSICS_EVENT_CHANGED_FIELD = getField(BlockPhysicsEvent::class.java, true, "changed")
    val PALETTED_CONTAINER_DATA_FIELD = getField(PalettedContainer::class.java, true, "SRF(net.minecraft.world.level.chunk.PalettedContainer data)")
    val PALETTED_CONTAINER_DATA_PALETTE_FIELD = getField(PALETTED_CONTAINER_DATA_CLASS, true, "SRF(net.minecraft.world.level.chunk.PalettedContainer\$Data palette)")
    val PALETTED_CONTAINER_DATA_STORAGE_FIELD = getField(PALETTED_CONTAINER_DATA_CLASS, true, "SRF(net.minecraft.world.level.chunk.PalettedContainer\$Data storage)")
    val LINEAR_PALETTE_VALUES_FIELD = getField(LinearPalette::class.java, true, "SRF(net.minecraft.world.level.chunk.LinearPalette values)")
    val HASH_MAP_PALETTE_VALUES_FIELD = getField(HashMapPalette::class.java, true, "SRF(net.minecraft.world.level.chunk.HashMapPalette values)")
    
}
