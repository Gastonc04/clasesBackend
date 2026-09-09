package ar.edu.utn.frc.backend;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ar.edu.utn.frc.backend.modelo.Auto;

public class Collection {

	/*
	 * Devuelve la cantidad de autos de una marca y un año determinado
	 *
	 * @param autos array de autos
	 * @param marca marca a buscar
	 * @param anio año a buscar
	 * @return cantidad de autos de una marca y un año determinado
	 */
	public static List<Auto> obtenerAutos() {
		String path = ClassLoader.getSystemResource("autos.csv").getPath();
		try {
			String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
			File file = new File(decodedPath);
			try (Stream<String> lines = Files.lines(file.toPath())) {
				return lines.map(Auto::fromString).collect(Collectors.toList());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static long obtenerCantidadPorMarcaYAnio(List<Auto> autos, String marca, int anio) {

		return autos.stream()
				.filter(a -> a.getMarca().equalsIgnoreCase(marca) && a.getAnio() == anio)
				.count();
	}

	/*
	 * Devuelve la cantidad de autos convertibles
	 *
	 * @param autos array de autos
	 * @return cantidad de autos convertibles
	 */
	public static long obtenerCantidadDeConvertibles(List<Auto> autos) {
		return autos.stream()
				.filter(a -> a.getTipos().contains("Convertible"))
				.count();
	}

	/*
	 * Devuelve un array con las marcas que vendan sedanes
	 *
	 * @param autos array de autos
	 * @return array de marcas
	 */
	public static Set<String> obtenerLasMarcasQueVendanSedanes(List<Auto> autos) {
		return null;
	}

	/*
	 * Devuelve un map con la cantidad de autos por marca
	 *
	 * @param autos array de autos
	 * @return map con la cantidad de autos por marca
	 */
	public static Map<String, Long> obtenerCantidadDeAutosPorMarca(List<Auto> autos) {
		return null;
	}

	/*
	 * Devuelve un map con la cantidad de autos por año
	 *
	 * @param autos array de autos
	 * @return map con la cantidad de autos por año
	 */
	public static Map<Integer, Long> obtenerCantidadDeAutosPorAnio(List<Auto> autos) {
		return null;

	}

	/*
	 * Devuelve los autos filtrados
	 * @param autos array de autos
	 * @param filtro filtro a aplicar
	 * @return autos filtrados
	 */
	public static List<Auto> filtrarAutos(List<Auto> autos, Predicate<Auto> filtro) {
		return null;
	}

	/**
	 * Devuelve una lista de autos que no sean del tipo especificado
	 *
	 * @param autos array de autos
	 * @param tipo  tipo a filtrar
	 * @return lista de autos que no sean del tipo especificado
	 */
	public static List<Auto> obtenerTodosLosAutosQueNoSeanDelTipo(List<Auto> autos, String tipo) {
		return null;
	}

	/*
	 * Obtener las marcas que tengan modelos con números en el nombre
	 * @param autos array de autos
	 * @return marcas que tengan modelos con números en el nombre
	 */
	public static Set<String> obtenerLasMarcasQueTenganModelosConNumeros(List<Auto> autos) {
		return null;
	}

	/*
	 * Leer el archivo autos.csv y devuelve un array de autos
	 * @return array de autos
	 */
	public static List<Auto> obtenerAutos() {
		String path = ClassLoader.getSystemResource("autos.csv").getPath();

		return null;
	}
}
