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

import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Login.*;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Play.*;
import static io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId.Status.*;

import io.github.sparky983.diorite.net.packet.clientbound.ClientBoundPacketId;
import io.github.sparky983.diorite.net.packet.clientbound.login.*;
import io.github.sparky983.diorite.net.packet.clientbound.status.*;
import io.github.sparky983.diorite.net.packet.clientbound.play.*;

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
                .registerPacket(ClientBoundPacketId.Login.DISCONNECT, io.github.sparky983.diorite.net.packet.clientbound.login.DisconnectPacket::new)
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
                .registerPacket(ClientBoundPacketId.Play.DISCONNECT, io.github.sparky983.diorite.net.packet.clientbound.play.DisconnectPacket::new)
                .registerPacket(ENTITY_STATUS, EntityStatusPacket::new)
                .registerPacket(EXPLOSION, ExplosionPacket::new)
                .registerPacket(UNLOAD_CHUNK, UnloadChunkPacket::new)
                .registerPacket(CHANGE_GAME_STATE, ChangeGameStatePacket::new)
                .registerPacket(OPEN_HORSE_WINDOW, OpenHorseWindowPacket::new)
                .registerPacket(INITIALIZE_WORLD_BORDER, InitializeWorldBorderPacket::new)
                .registerPacket(KEEP_ALIVE, KeepAlivePacket::new)
                .registerPacket(CHUNK_DATA, ChunkDataPacket::new)
                .registerPacket(EFFECT, EffectPacket::new)
                .registerPacket(PARTICLE, ParticlePacket::new)
                .registerPacket(UPDATE_LIGHT, UpdateLightPacket::new)
                .registerPacket(JOIN_GAME, JoinGamePacket::new)
                .registerPacket(MAP_DATA, MapDataPacket::new)
                .registerPacket(TRADE_LIST, TradeListPacket::new)
                .registerPacket(ENTITY_POSITION, EntityMovementPacket.Position::new)
                .registerPacket(ENTITY_POSITION_AND_ROTATION, EntityMovementPacket.PositionAndRotation::new)
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
