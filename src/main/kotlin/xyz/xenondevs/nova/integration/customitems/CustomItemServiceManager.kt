package xyz.xenondevs.nova.integration.customitems

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.nova.integration.Integration
import xyz.xenondevs.nova.integration.customitems.plugin.ItemsAdder
import xyz.xenondevs.nova.integration.customitems.plugin.Oraxen
import xyz.xenondevs.nova.util.runAsyncTask
import xyz.xenondevs.nova.util.runTask
import java.util.concurrent.CountDownLatch

object CustomItemServiceManager {
    
    private val PLUGINS: List<CustomItemService> = listOf(ItemsAdder, Oraxen)
        .filter(Integration::isInstalled)
    private val LOAD_DELAYING_PLUGINS_AMOUNT = PLUGINS.count(CustomItemService::requiresLoadDelay)
    val READY_LATCH = CountDownLatch(LOAD_DELAYING_PLUGINS_AMOUNT)
    
    fun placeItem(item: ItemStack, location: Location, playEffects: Boolean): Boolean {
        return PLUGINS.any { it.placeBlock(item, location, playEffects) }
    }
    
    fun breakBlock(block: Block, tool: ItemStack?, playEffects: Boolean): List<ItemStack>? {
        return PLUGINS.firstNotNullOfOrNull { it.breakBlock(block, tool, playEffects) }
    }
    
    fun getItemByName(name: String): ItemStack? {
        return PLUGINS.firstNotNullOfOrNull { it.getItemByName(name) }
    }
    
    fun getNameKey(item: ItemStack): String? {
        return PLUGINS.firstNotNullOfOrNull { it.getNameKey(item) }
    }
    
    fun hasNamespace(namespace: String): Boolean {
        return PLUGINS.any { it.hasNamespace(namespace) }
    }
    
    fun runAfterDataLoad(run: () -> Unit) {
        if (LOAD_DELAYING_PLUGINS_AMOUNT != 0) {
            runAsyncTask {
                READY_LATCH.await()
                runTask(run)
            }
        } else run()
    }
    
}