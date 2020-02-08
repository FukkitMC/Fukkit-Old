/*
 * Copyright 2020 ramidzkh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.fukkitmc.fukkit.mixins.things.sender;

import io.github.fukkitmc.fukkit.things.ICommandListenerThing;
import net.minecraft.server.CommandListenerWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.RemoteControlCommandListener;
import org.bukkit.command.CommandSender;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RemoteControlCommandListener.class)
public class RemoteControlCommandListenerMixin implements ICommandListenerThing {

    @Shadow @Final private MinecraftServer server;

    @Override
    public CommandSender getBukkitSender(CommandListenerWrapper wrapper) {
        return server.console;
    }
}
