package xyz.xenondevs.nova.world.generation.ruletest

import net.minecraft.core.BlockPos
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import xyz.xenondevs.nova.data.world.WorldDataManager
import xyz.xenondevs.nova.material.BlockNovaMaterial
import xyz.xenondevs.nova.world.generation.ExperimentalLevelGen
import net.minecraft.world.level.block.state.BlockState as MojangState

@ExperimentalLevelGen
abstract class NovaMaterialTest : NovaRuleTest() {
    
    final override fun test(level: Level, pos: BlockPos, state: MojangState, random: RandomSource): Boolean {
        val material = WorldDataManager.getWorldGenMaterial(pos, level) ?: return false
        return test(material, level, pos, state, random)
    }
    
    abstract fun test(material: BlockNovaMaterial, level: Level, pos: BlockPos, state: MojangState, random: RandomSource): Boolean
    
}