package net.querz.mcaselector.version;

import net.querz.mcaselector.debug.Debug;
import net.querz.mcaselector.version.anvil112.*;
import net.querz.mcaselector.version.anvil113.*;
import net.querz.mcaselector.version.anvil114.*;
import net.querz.mcaselector.version.anvil115.*;
import net.querz.mcaselector.version.anvil116.*;
import net.querz.mcaselector.version.anvil117.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class VersionController {

	private VersionController() {}

	public static ChunkFilter getChunkFilter(int dataVersion) {
		return Mapping.match(dataVersion).getChunkFilter();
	}

	public static ChunkMerger getChunkMerger(int dataVersion) {
		return Mapping.match(dataVersion).getChunkMerger();
	}

	public static ChunkRelocator getChunkRelocator(int dataVersion) {
		return Mapping.match(dataVersion).getChunkRelocator();
	}

	public static ChunkRenderer getChunkRenderer(int dataVersion) {
		return Mapping.match(dataVersion).getChunkRenderer();
	}

	public static ColorMapping getColorMapping(int dataVersion) {
		return Mapping.match(dataVersion).getColorMapping();
	}

	public static PoiRelocator getPoiRelocator(int dataVersion) {
		return Mapping.match(dataVersion).getPOIRelocator();
	}

	public static EntityRelocator getEntityRelocator(int dataVersion) {
		return Mapping.match(dataVersion).getEntityRelocator();
	}

	public static EntityFilter getEntityFilter(int dataVersion) {
		return Mapping.match(dataVersion).getEntityFilter();
	}

	private static final Map<Class<? extends ChunkFilter>, ChunkFilter> chunkFilterInstances = new ConcurrentHashMap<>();
	private static final Map<Class<? extends ChunkMerger>, ChunkMerger> chunkMergerInstances = new ConcurrentHashMap<>();
	private static final Map<Class<? extends ChunkRelocator>, ChunkRelocator> chunkRelocatorInstances = new ConcurrentHashMap<>();
	private static final Map<Class<? extends PoiRelocator>, PoiRelocator> poiRelocatorInstances = new ConcurrentHashMap<>();
	private static final Map<Class<? extends EntityRelocator>, EntityRelocator> entityRelocatorInstances = new ConcurrentHashMap<>();
	private static final Map<Class<? extends EntityFilter>, EntityFilter> entityFilterInstances = new ConcurrentHashMap<>();
	private static final Map<Class<? extends ChunkRenderer>, ChunkRenderer> chunkRendererInstances = new ConcurrentHashMap<>();
	private static final Map<Class<? extends ColorMapping>, ColorMapping> colorMappingInstances = new ConcurrentHashMap<>();

	private enum Mapping {

		ANVIL112(0, 1343, Anvil112ChunkFilter.class, Anvil112ChunkMerger.class, Anvil112ChunkRelocator.class, Anvil112PoiRelocator.class, Anvil112EntityRelocator.class, Anvil112EntityFilter.class, Anvil112ChunkRenderer.class, Anvil112ColorMapping.class),
		ANVIL113(1344, 1631, Anvil113ChunkFilter.class, Anvil113ChunkMerger.class, Anvil113ChunkRelocator.class, Anvil112PoiRelocator.class, Anvil112EntityRelocator.class, Anvil112EntityFilter.class, Anvil113ChunkRenderer.class, Anvil113ColorMapping.class),
		ANVIL114(1632, 2201, Anvil113ChunkFilter.class, Anvil113ChunkMerger.class, Anvil114ChunkRelocator.class, Anvil114PoiRelocator.class, Anvil112EntityRelocator.class, Anvil112EntityFilter.class, Anvil113ChunkRenderer.class, Anvil114ColorMapping.class),
		ANVIL115(2202, 2526, Anvil115ChunkFilter.class, Anvil115ChunkMerger.class, Anvil115ChunkRelocator.class, Anvil114PoiRelocator.class, Anvil112EntityRelocator.class, Anvil112EntityFilter.class, Anvil115ChunkRenderer.class, Anvil115ColorMapping.class),
		ANVIL116(2527, 2686, Anvil116ChunkFilter.class, Anvil115ChunkMerger.class, Anvil116ChunkRelocator.class, Anvil114PoiRelocator.class, Anvil112EntityRelocator.class, Anvil112EntityFilter.class, Anvil116ChunkRenderer.class, Anvil116ColorMapping.class),
		ANVIL117(2687, Integer.MAX_VALUE, Anvil117ChunkFilter.class, Anvil115ChunkMerger.class, Anvil117ChunkRelocator.class, Anvil114PoiRelocator.class, Anvil117EntityRelocator.class, Anvil117EntityFilter.class, Anvil117ChunkRenderer.class, Anvil117ColorMapping.class);

		private final int minVersion, maxVersion;
		private final Class<? extends ChunkFilter> chunkFilter;
		private final Class<? extends ChunkMerger> chunkMerger;
		private final Class<? extends ChunkRelocator> chunkRelocator;
		private final Class<? extends PoiRelocator> poiRelocator;
		private final Class<? extends EntityRelocator> entityRelocator;
		private final Class<? extends EntityFilter> entityFilter;

		private final Class<? extends ChunkRenderer> chunkRenderer;
		private final Class<? extends ColorMapping> colorMapping;

		private static final Map<Integer, Mapping> mappingCache = new HashMap<>();

		Mapping(
				int minVersion,
				int maxVersion,
				Class<? extends ChunkFilter> chunkFilter,
				Class<? extends ChunkMerger> chunkMerger,
				Class<? extends ChunkRelocator> chunkRelocator,
				Class<? extends PoiRelocator> poiRelocator,
				Class<? extends EntityRelocator> entityRelocator,
				Class<? extends EntityFilter> entityFilter,
				Class<? extends ChunkRenderer> chunkRenderer,
				Class<? extends ColorMapping> colorMapping) {
			this.minVersion = minVersion;
			this.maxVersion = maxVersion;
			this.chunkFilter = chunkFilter;
			this.chunkMerger = chunkMerger;
			this.chunkRelocator = chunkRelocator;
			this.poiRelocator = poiRelocator;
			this.entityRelocator = entityRelocator;
			this.entityFilter = entityFilter;
			this.chunkRenderer = chunkRenderer;
			this.colorMapping = colorMapping;
		}

		ChunkFilter getChunkFilter() {
			return chunkFilterInstances.computeIfAbsent(chunkFilter, k -> {
				try {
					return k.getConstructor().newInstance();
				} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
					Debug.dumpException(String.format("failed to create new instance of ChunkFilter for %d-%d", minVersion, maxVersion), ex);
				}
				return null;
			});
		}

		ChunkMerger getChunkMerger() {
			return chunkMergerInstances.computeIfAbsent(chunkMerger, k -> {
				try {
					return k.getConstructor().newInstance();
				} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
					Debug.dumpException(String.format("failed to create new instance of ChunkMerger for %d-%d", minVersion, maxVersion), ex);
				}
				return null;
			});
		}

		ChunkRelocator getChunkRelocator() {
			return chunkRelocatorInstances.computeIfAbsent(chunkRelocator, k -> {
				try {
					return k.getConstructor().newInstance();
				} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
					Debug.dumpException(String.format("failed to create new instance of ChunkRelocator for %d-%d", minVersion, maxVersion), ex);
				}
				return null;
			});
		}

		PoiRelocator getPOIRelocator() {
			return poiRelocatorInstances.computeIfAbsent(poiRelocator, k -> {
				try {
					return k.getConstructor().newInstance();
				} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
					Debug.dumpException(String.format("failed to create new instance of POIRelocator for %d-%d", minVersion, maxVersion), ex);
				}
				return null;
			});
		}

		EntityRelocator getEntityRelocator() {
			return entityRelocatorInstances.computeIfAbsent(entityRelocator, k -> {
				try {
					return k.getConstructor().newInstance();
				} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
					Debug.dumpException(String.format("failed to create new instance of EntityRelocator for %d-%d", minVersion, maxVersion), ex);
				}
				return null;
			});
		}

		EntityFilter getEntityFilter() {
			return entityFilterInstances.computeIfAbsent(entityFilter, k -> {
				try {
					return k.getConstructor().newInstance();
				} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
					Debug.dumpException(String.format("failed to create new instance of EntityFilter for %d-%d", minVersion, maxVersion), ex);
				}
				return null;
			});
		}

		ChunkRenderer getChunkRenderer() {
			return chunkRendererInstances.computeIfAbsent(chunkRenderer, k -> {
				try {
					return k.getConstructor().newInstance();
				} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
					Debug.dumpException(String.format("failed to create new instance of ChunkRenderer for %d-%d", minVersion, maxVersion), ex);
				}
				return null;
			});
		}

		ColorMapping getColorMapping() {
			return colorMappingInstances.computeIfAbsent(colorMapping, k -> {
				try {
					return k.getConstructor().newInstance();
				} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
					Debug.dumpException(String.format("failed to create new instance of ColorMapping for %d-%d", minVersion, maxVersion), ex);
				}
				return null;
			});
		}

		static Mapping match(int dataVersion) {
			Mapping mapping = mappingCache.get(dataVersion);
			if (mapping == null) {
				for (Mapping m : Mapping.values()) {
					if (m.minVersion <= dataVersion && m.maxVersion >= dataVersion) {
						mappingCache.put(dataVersion, m);
						return m;
					}
				}
				throw new IllegalArgumentException("invalid DataVersion: " + dataVersion);
			}
			return mapping;
		}
	}
}
