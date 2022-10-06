/*
 * Copyright 2022 Sparky
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

package io.github.sparky983.diorite.net.packet;

import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Login.ENCRYPTION_REQUEST;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Login.LOGIN_PLUGIN_REQUEST;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Login.LOGIN_SUCCESS;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Login.SET_COMPRESSION;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ACKNOWLEDGE_PLAYER_DIGGING;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ACTION_BAR;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ADVANCEMENTS;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ATTACH_ENTITY;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.BLOCK_ACTION;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.BLOCK_BREAK_ANIMATION;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.BLOCK_CHANGE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.BLOCK_ENTITY_DATA;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.BOSS_BAR;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.CAMERA;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.CHANGE_GAME_STATE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.CHAT_MESSAGE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.CHUNK_DATA;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.CLEAR_TITLES;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.CLOSE_WINDOW;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.COLLECT_ITEM;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.CRAFT_RECIPE_RESPONSE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.DEATH_COMBAT_EVENT;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.DECLARE_COMMANDS;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.DECLARE_RECIPES;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.DESTROY_ENTITIES;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.DISPLAY_SCOREBOARD;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.EFFECT;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.END_COMBAT_EVENT;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ENTER_COMBAT_EVENT;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ENTITY_ANIMATION;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ENTITY_EFFECT;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ENTITY_EQUIPMENT;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ENTITY_HEAD_LOOK;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ENTITY_METADATA;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ENTITY_POSITION;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ENTITY_POSITION_AND_ROTATION;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ENTITY_PROPERTIES;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ENTITY_ROTATION;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ENTITY_SOUND_EFFECT;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ENTITY_STATUS;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ENTITY_TELEPORT;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.ENTITY_VELOCITY;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.EXPLOSION;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.FACE_PLAYER;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.HELD_ITEM_CHANGE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.INITIALIZE_WORLD_BORDER;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.JOIN_GAME;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.KEEP_ALIVE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.MAP_DATA;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.MULTI_BLOCK_CHANGE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.NAMED_SOUND_EFFECT;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.NBT_QUERY_RESPONSE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.OPEN_BOOK;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.OPEN_HORSE_WINDOW;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.OPEN_SIGN_EDITOR;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.OPEN_WINDOW;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.PARTICLE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.PING;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.PLAYER_ABILITIES;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.PLAYER_INFO;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.PLAYER_LIST_HEADER_FOOTER;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.PLAYER_POSITION_AND_LOOK;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.PLUGIN_MESSAGE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.REMOVE_ENTITY_EFFECT;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.RESOURCE_PACK_SEND;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.RESPAWN;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SCOREBOARD_OBJECTIVE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SCULK_VIBRATION_SIGNAL;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SELECT_ADVANCEMENT_TAB;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SERVER_DIFFICULTY;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SET_COOLDOWN;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SET_EXPERIENCE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SET_PASSENGERS;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SET_SLOT;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SET_TITLE_SUBTITLE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SET_TITLE_TEXT;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SET_TITLE_TIMES;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SOUND_EFFECT;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SPAWN_ENTITY;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SPAWN_EXPERIENCE_ORB;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SPAWN_LIVING_ENTITY;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SPAWN_PAINTING;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SPAWN_PLAYER;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.SPAWN_POSITION;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.STATISTICS;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.STOP_SOUND;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.TAB_COMPLETE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.TAGS;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.TEAMS;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.TIME_UPDATE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.TRADE_LIST;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.UNLOAD_CHUNK;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.UNLOCK_RECIPES;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.UPDATE_HEALTH;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.UPDATE_LIGHT;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.UPDATE_SCORE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.UPDATE_SIMULATION_DISTANCE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.UPDATE_VIEW_DISTANCE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.UPDATE_VIEW_POSITION;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.VEHICLE_MOVE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.WINDOW_ITEMS;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.WINDOW_PROPERTY;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.WORLD_BORDER_CENTER;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.WORLD_BORDER_LERP_SIZE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.WORLD_BORDER_SIZE;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.WORLD_BORDER_WARNING_DELAY;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.WORLD_BORDER_WARNING_REACH;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Status.PONG;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Status.RESPONSE;

import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.net.packet.clientbound.login.EncryptionRequestPacket;
import io.github.sparky983.diorite.net.packet.clientbound.login.LoginPluginRequestPacket;
import io.github.sparky983.diorite.net.packet.clientbound.login.LoginSuccessPacket;
import io.github.sparky983.diorite.net.packet.clientbound.login.SetCompressionPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.AcknowledgePlayerDiggingPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.ActionBarPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.AdvancementsPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.AttachEntityPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.BlockActionPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.BlockBreakAnimationPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.BlockChangePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.BlockEntityDataPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.BossBarPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.CameraPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.ChangeGameStatePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.ChatMessagePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.ChunkDataAndUpdateLightPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.ClearTitlesPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.CloseWindowPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.CollectItemPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.CraftRecipeResponsePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.DeathCombatEventPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.DeclareCommandsPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.DeclareRecipesPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.DestroyEntitiesPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.DisplayScoreboardPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.EffectPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.EndCombatEventPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.EnterCombatEventPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.EntityAnimationPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.EntityEffectPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.EntityEquipmentPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.EntityHeadLookPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.EntityMetadataPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.EntityMovementPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.EntityPropertiesPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.EntitySoundEffectPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.EntityStatusPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.EntityTeleportPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.EntityVelocityPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.ExplosionPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.FacePlayerPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.HeldItemChangePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.InitializeWorldBorderPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.JoinGamePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.KeepAlivePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.MapDataPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.MultiBlockChangePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.NamedSoundEffectPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.NbtQueryResponsePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.OpenBookPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.OpenHorseWindowPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.OpenSignEditorPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.OpenWindowPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.ParticlePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.PingPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.PlayerAbilitiesPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.PlayerInfoPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.PlayerListHeaderAndFooterPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.PlayerPositionAndLookPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.PluginMessagePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.RemoveEntityEffectPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.ResourcePackSendPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.RespawnPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.ScoreboardObjectivePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.SculkVibrationSignalPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.SelectAdvancementTabPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.ServerDifficultyPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.SetCooldownPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.SetExperiencePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.SetPassengersPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.SetSlotPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.SetTitleSubtitlePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.SetTitleTextPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.SetTitleTimesPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.SoundEffectPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.SpawnEntityPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.SpawnExperienceOrbPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.SpawnLivingEntityPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.SpawnPaintingPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.SpawnPlayerPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.SpawnPositionPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.StatisticsPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.StopSoundPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.TabCompletePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.TagsPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.TeamsPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.TimeUpdatePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.TradeListPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.UnloadChunkPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.UnlockRecipesPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.UpdateHealthPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.UpdateLightPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.UpdateScorePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.UpdateSimulationDistancePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.UpdateViewDistancePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.UpdateViewPositionPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.VehicleMovePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.WindowItemsPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.WindowPropertyPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.WorldBorderCenterPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.WorldBorderLerpSizePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.WorldBorderSizePacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.WorldBorderWarningDelayPacket;
import io.github.sparky983.diorite.net.packet.clientbound.play.WorldBorderWarningReachPacket;
import io.github.sparky983.diorite.net.packet.clientbound.status.PongPacket;
import io.github.sparky983.diorite.net.packet.clientbound.status.ResponsePacket;

public final class PacketRegistries {

    /**
     * Provides a way to lazily retrieve client packet registries via class loading.
     *
     * @since 1.0.0
     */
    public static final class Client {

        public static final PacketRegistry HANDSHAKING = PacketRegistry.EMPTY;

        public static final PacketRegistry STATUS = PacketRegistry.builder()
                .registerPacket(RESPONSE, ResponsePacket::new)
                .registerPacket(PONG, PongPacket::new)
                .build();

        public static final PacketRegistry LOGIN = PacketRegistry.builder()
                .registerPacket(ClientBoundPacketId.Login.DISCONNECT,
                        io.github.sparky983.diorite.net.packet.clientbound.login.DisconnectPacket::new)
                .registerPacket(ENCRYPTION_REQUEST, EncryptionRequestPacket::new)
                .registerPacket(LOGIN_SUCCESS, LoginSuccessPacket::new)
                .registerPacket(SET_COMPRESSION, SetCompressionPacket::new)
                .registerPacket(LOGIN_PLUGIN_REQUEST, LoginPluginRequestPacket::new)
                .build();

        public static final PacketRegistry PLAY = PacketRegistry.builder()
                .registerPacket(SPAWN_ENTITY, SpawnEntityPacket::new)
                .registerPacket(SPAWN_EXPERIENCE_ORB, SpawnExperienceOrbPacket::new)
                .registerPacket(SPAWN_LIVING_ENTITY, SpawnLivingEntityPacket::new)
                .registerPacket(SPAWN_PAINTING, SpawnPaintingPacket::new)
                .registerPacket(SPAWN_PLAYER, SpawnPlayerPacket::new)
                .registerPacket(SCULK_VIBRATION_SIGNAL, SculkVibrationSignalPacket::new)
                .registerPacket(ENTITY_ANIMATION, EntityAnimationPacket::new)
                .registerPacket(STATISTICS, StatisticsPacket::new)
                .registerPacket(ACKNOWLEDGE_PLAYER_DIGGING, AcknowledgePlayerDiggingPacket::new)
                .registerPacket(BLOCK_BREAK_ANIMATION, BlockBreakAnimationPacket::new)
                .registerPacket(BLOCK_ENTITY_DATA, BlockEntityDataPacket::new)
                .registerPacket(BLOCK_ACTION, BlockActionPacket::new)
                .registerPacket(BLOCK_CHANGE, BlockChangePacket::new)
                .registerPacket(BOSS_BAR, BossBarPacket::new)
                .registerPacket(SERVER_DIFFICULTY, ServerDifficultyPacket::new)
                .registerPacket(CHAT_MESSAGE, ChatMessagePacket::new)
                .registerPacket(CLEAR_TITLES, ClearTitlesPacket::new)
                .registerPacket(TAB_COMPLETE, TabCompletePacket::new)
                .registerPacket(DECLARE_COMMANDS, DeclareCommandsPacket::new)
                .registerPacket(CLOSE_WINDOW, CloseWindowPacket::new)
                .registerPacket(WINDOW_ITEMS, WindowItemsPacket::new)
                .registerPacket(WINDOW_PROPERTY, WindowPropertyPacket::new)
                .registerPacket(SET_SLOT, SetSlotPacket::new)
                .registerPacket(SET_COOLDOWN, SetCooldownPacket::new)
                .registerPacket(PLUGIN_MESSAGE, PluginMessagePacket::new)
                .registerPacket(NAMED_SOUND_EFFECT, NamedSoundEffectPacket::new)
                .registerPacket(ClientBoundPacketId.Play.DISCONNECT,
                        io.github.sparky983.diorite.net.packet.clientbound.play.DisconnectPacket::new)
                .registerPacket(ENTITY_STATUS, EntityStatusPacket::new)
                .registerPacket(EXPLOSION, ExplosionPacket::new)
                .registerPacket(UNLOAD_CHUNK, UnloadChunkPacket::new)
                .registerPacket(CHANGE_GAME_STATE, ChangeGameStatePacket::new)
                .registerPacket(OPEN_HORSE_WINDOW, OpenHorseWindowPacket::new)
                .registerPacket(INITIALIZE_WORLD_BORDER, InitializeWorldBorderPacket::new)
                .registerPacket(KEEP_ALIVE, KeepAlivePacket::new)
                .registerPacket(CHUNK_DATA, ChunkDataAndUpdateLightPacket::new)
                .registerPacket(EFFECT, EffectPacket::new)
                .registerPacket(PARTICLE, ParticlePacket::new)
                .registerPacket(UPDATE_LIGHT, UpdateLightPacket::new)
                .registerPacket(JOIN_GAME, JoinGamePacket::new)
                .registerPacket(MAP_DATA, MapDataPacket::new)
                .registerPacket(TRADE_LIST, TradeListPacket::new)
                .registerPacket(ENTITY_POSITION, EntityMovementPacket.Position::new)
                .registerPacket(ENTITY_POSITION_AND_ROTATION,
                        EntityMovementPacket.PositionAndRotation::new)
                .registerPacket(ENTITY_ROTATION, EntityMovementPacket.Rotation::new)
                .registerPacket(VEHICLE_MOVE, VehicleMovePacket::new)
                .registerPacket(OPEN_BOOK, OpenBookPacket::new)
                .registerPacket(OPEN_WINDOW, OpenWindowPacket::new)
                .registerPacket(OPEN_SIGN_EDITOR, OpenSignEditorPacket::new)
                .registerPacket(PING, PingPacket::new)
                .registerPacket(CRAFT_RECIPE_RESPONSE, CraftRecipeResponsePacket::new)
                .registerPacket(PLAYER_ABILITIES, PlayerAbilitiesPacket::new)
                .registerPacket(END_COMBAT_EVENT, EndCombatEventPacket::new)
                .registerPacket(ENTER_COMBAT_EVENT, EnterCombatEventPacket::new)
                .registerPacket(DEATH_COMBAT_EVENT, DeathCombatEventPacket::new)
                .registerPacket(PLAYER_INFO, PlayerInfoPacket::new)
                .registerPacket(FACE_PLAYER, FacePlayerPacket::new)
                .registerPacket(PLAYER_POSITION_AND_LOOK, PlayerPositionAndLookPacket::new)
                .registerPacket(UNLOCK_RECIPES, UnlockRecipesPacket::new)
                .registerPacket(DESTROY_ENTITIES, DestroyEntitiesPacket::new)
                .registerPacket(REMOVE_ENTITY_EFFECT, RemoveEntityEffectPacket::new)
                .registerPacket(RESOURCE_PACK_SEND, ResourcePackSendPacket::new)
                .registerPacket(RESPAWN, RespawnPacket::new)
                .registerPacket(ENTITY_HEAD_LOOK, EntityHeadLookPacket::new)
                .registerPacket(MULTI_BLOCK_CHANGE, MultiBlockChangePacket::new)
                .registerPacket(SELECT_ADVANCEMENT_TAB, SelectAdvancementTabPacket::new)
                .registerPacket(ACTION_BAR, ActionBarPacket::new)
                .registerPacket(WORLD_BORDER_CENTER, WorldBorderCenterPacket::new)
                .registerPacket(WORLD_BORDER_LERP_SIZE, WorldBorderLerpSizePacket::new)
                .registerPacket(WORLD_BORDER_SIZE, WorldBorderSizePacket::new)
                .registerPacket(WORLD_BORDER_WARNING_DELAY, WorldBorderWarningDelayPacket::new)
                .registerPacket(WORLD_BORDER_WARNING_REACH, WorldBorderWarningReachPacket::new)
                .registerPacket(CAMERA, CameraPacket::new)
                .registerPacket(HELD_ITEM_CHANGE, HeldItemChangePacket::new)
                .registerPacket(UPDATE_VIEW_POSITION, UpdateViewPositionPacket::new)
                .registerPacket(UPDATE_VIEW_DISTANCE, UpdateViewDistancePacket::new)
                .registerPacket(SPAWN_POSITION, SpawnPositionPacket::new)
                .registerPacket(DISPLAY_SCOREBOARD, DisplayScoreboardPacket::new)
                .registerPacket(ENTITY_METADATA, EntityMetadataPacket::new)
                .registerPacket(ATTACH_ENTITY, AttachEntityPacket::new)
                .registerPacket(ENTITY_VELOCITY, EntityVelocityPacket::new)
                .registerPacket(ENTITY_EQUIPMENT, EntityEquipmentPacket::new)
                .registerPacket(SET_EXPERIENCE, SetExperiencePacket::new)
                .registerPacket(UPDATE_HEALTH, UpdateHealthPacket::new)
                .registerPacket(SCOREBOARD_OBJECTIVE, ScoreboardObjectivePacket::new)
                .registerPacket(SET_PASSENGERS, SetPassengersPacket::new)
                .registerPacket(TEAMS, TeamsPacket::new)
                .registerPacket(UPDATE_SCORE, UpdateScorePacket::new)
                .registerPacket(UPDATE_SIMULATION_DISTANCE, UpdateSimulationDistancePacket::new)
                .registerPacket(SET_TITLE_SUBTITLE, SetTitleSubtitlePacket::new)
                .registerPacket(TIME_UPDATE, TimeUpdatePacket::new)
                .registerPacket(SET_TITLE_TEXT, SetTitleTextPacket::new)
                .registerPacket(SET_TITLE_TIMES, SetTitleTimesPacket::new)
                .registerPacket(ENTITY_SOUND_EFFECT, EntitySoundEffectPacket::new)
                .registerPacket(SOUND_EFFECT, SoundEffectPacket::new)
                .registerPacket(STOP_SOUND, StopSoundPacket::new)
                .registerPacket(PLAYER_LIST_HEADER_FOOTER, PlayerListHeaderAndFooterPacket::new)
                .registerPacket(NBT_QUERY_RESPONSE, NbtQueryResponsePacket::new)
                .registerPacket(COLLECT_ITEM, CollectItemPacket::new)
                .registerPacket(ENTITY_TELEPORT, EntityTeleportPacket::new)
                .registerPacket(ADVANCEMENTS, AdvancementsPacket::new)
                .registerPacket(ENTITY_PROPERTIES, EntityPropertiesPacket::new)
                .registerPacket(ENTITY_EFFECT, EntityEffectPacket::new)
                .registerPacket(DECLARE_RECIPES, DeclareRecipesPacket::new)
                .registerPacket(TAGS, TagsPacket::new)
                .build();
    }

    // TODO(Sparky983): Server
    public static final class Server {

    }
}
