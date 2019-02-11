import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestRouter {
    private static final String PARAMS_FILE = "path_params.txt";
    private static final String RESULTS_FILE = "path_results.txt";
    private static final int NUM_TESTS = 8;
    private static final String OSM_DB_PATH = "../library-sp18/data/berkeley-2018.osm.xml";
    private static GraphDB graph;
    private static boolean initialized = false;

    @Before
    public void setUp() throws Exception {
        if (initialized) {
            return;
        }
        graph = new GraphDB(OSM_DB_PATH);
        initialized = true;
    }

    @Test
    public void testShortestPathTiny() {
        double end_lat = 37.87321369908015;
        double start_lon = -122.28425043246807;
        double start_lat = 37.83935748693148;
        double end_lon = -122.29155446301381;
        List<Long> actual = Router.shortestPath(graph, start_lon, start_lat, end_lon, end_lat);
        for(Long id : actual) {
            System.out.print(id + ", ");
        }
        System.out.println("");
//        53102030, 280699448, 280699449, 280699442, 1237060562, 4168227702, 1237060564, 283261586, 283261585, 1237060518, 4168260345, 1237060522, 283261532, 4168260347, 283261531, 283261584, 1237060533, 4168260348, 283261583, 1237060542, 4168260350, 283261582, 1237062764, 283261581, 4550436142, 1237062766, 283261530, 4550436144, 4168260355, 283261517, 1237062760, 283261522, 1237062769, 283261516, 4168260357, 283261515, 4235206800, 283261510, 53043845, 4235206807, 53130811, 53130814, 4235206820, 1612672411, 283261509, 2644106900, 4168260361, 2644106912, 4213425299, 2644106917, 4213425302, 53109969, 4213425305, 243673081, 4168260368, 4213425309, 243673082, 4213425313, 1613874217, 1613874246, 4213425315, 243673084, 4213378290, 243673085, 4168260379, 4213378292, 243673087, 4213378295, 4213378299, 243673088, 4168260384, 4213378302, 243673089, 4213378305, 243673091, 1745072095, 243673093, 243673096, 243673098, 4213378310, 4213378313, 243673100, 4213378316, 4168148260, 4213378319, 243670956, 4213378322, 4213378324, 243673103, 4467802979, 4168148263, 4213378327, 243673105, 4213378331, 4213378332, 4213378334, 243673107, 4213378338, 4213378341, 243673110, 4168148267, 4213378346, 243673112, 4213378350, 4544254687, 4544260190, 4168148271, 4213378352, 243673115, 4213378356, 1671396836, 1670810118, 4213378364, 243673117, 4168148273, 4213378367, 243673120, 4213378370, 243673123, 3720819936, 4168148280, 4213378373, 243673125, 243673129, 4213378379, 1669800003, 1669807797, 4168148392, 4213378385, 243670962, 4213378489, 2131736236, 4168148402, 4213378491, 243673133, 4213378495, 4541872094, 4213378497, 243673135, 4213378498, 1789060525, 1789060548]> but was:<[53102030, 280699448, 280699449, 280699442, 1237060562, 4168227702, 1237060564, 283261586, 283261585, 53124567, 1237060521, 1237060525, 53130589, 1489303366, 53130592, 53107926, 1237060539, 4168260349, 53085991, 1237060549, 4168260352, 53088305, 4013648489, 4168260353, 1237062762, 53106458, 4550436143, 1237062768, 53106443, 4550436145, 53091951, 1237062763, 53126420, 1237062767, 53092791, 53064275, 4235206803, 53043845, 4235206807, 53130811, 53130814, 4235206820, 1612672411, 283261509, 2644106900, 4168260361, 2644106912, 4213425299, 2644106917, 4213425302, 53109969, 4213425305, 243673081, 4168260368, 4213425309, 243673082, 4213425313, 1613874217, 1613874246, 4213425315, 243673084, 4213378290, 243673085, 4168260379, 4213378292, 243673087, 4213378295, 4213378299, 243673088, 4168260384, 4213378302, 243673089, 4213378305, 243673091, 1745072095, 243673093, 243673096, 243673098, 4213378310, 4213378313, 243673100, 4213378316, 4168148260, 4213378319, 243670956, 4213378322, 4213378324, 243673103, 4467802979, 4168148263, 4213378327, 243673105, 4213378331, 4213378332, 4213378334, 243673107, 4213378338, 4213378341, 243673110, 4168148267, 4213378346, 243673112, 4213378350, 4544254687, 4544260190, 4168148271, 4213378352, 243673115, 4213378356, 1671396836, 1670810118, 4213378364, 243673117, 4168148273, 4213378367, 243673120, 4213378370, 243673123, 3720819936, 4168148280, 4213378373, 243673125, 243673129, 4213378379, 1669800003, 1669807797, 4168148392, 4213378385, 243670962, 4213378489, 2131736236, 4168148402, 4213378491, 243673133, 4213378495, 4541872094, 4213378497, 243673135, 4213378498, 1789060525, 1789060548
    }

    @Test
    public void testShortestPath() throws Exception {
        List<Map<String, Double>> testParams = paramsFromFile();
        List<List<Long>> expectedResults = resultsFromFile();

        for (int i = 0; i < NUM_TESTS; i++) {
            System.out.println(String.format("Running test: %d", i));
            Map<String, Double> params = testParams.get(i);
            List<Long> actual = Router.shortestPath(graph,
                    params.get("start_lon"), params.get("start_lat"),
                    params.get("end_lon"), params.get("end_lat"));
            List<Long> expected = expectedResults.get(i);
            assertEquals("Your results did not match the expected results", expected, actual);
        }
    }

    private List<Map<String, Double>> paramsFromFile() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(PARAMS_FILE), Charset.defaultCharset());
        List<Map<String, Double>> testParams = new ArrayList<>();
        int lineIdx = 2; // ignore comment lines
        for (int i = 0; i < NUM_TESTS; i++) {
            Map<String, Double> params = new HashMap<>();
            params.put("start_lon", Double.parseDouble(lines.get(lineIdx)));
            params.put("start_lat", Double.parseDouble(lines.get(lineIdx + 1)));
            params.put("end_lon", Double.parseDouble(lines.get(lineIdx + 2)));
            params.put("end_lat", Double.parseDouble(lines.get(lineIdx + 3)));
            testParams.add(params);
            lineIdx += 4;
        }
        return testParams;
    }

    private List<List<Long>> resultsFromFile() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(RESULTS_FILE), Charset.defaultCharset());
        List<List<Long>> expected = new ArrayList<>();
        int lineIdx = 2; // ignore comment lines
        for (int i = 0; i < NUM_TESTS; i++) {
            int numVertices = Integer.parseInt(lines.get(lineIdx));
            lineIdx++;
            List<Long> path = new ArrayList<>();
            for (int j = 0; j < numVertices; j++) {
                path.add(Long.parseLong(lines.get(lineIdx)));
                lineIdx++;
            }
            expected.add(path);
        }
        return expected;
    }
}
