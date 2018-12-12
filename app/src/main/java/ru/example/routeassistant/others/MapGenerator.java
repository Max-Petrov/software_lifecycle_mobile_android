package ru.kenguru.driverassistant.others;

import java.util.List;

import ru.kenguru.driverassistant.DAL.entities.Location;
import ru.kenguru.driverassistant.DAL.entities.RouteWithWaypoints;
import ru.kenguru.driverassistant.DAL.entities.Waypoint;

public class MapGenerator implements IMapGenerator {

    private static String htmlPage = "<!doctype html>" +
            "<html lang=\"ru\">" +
            "<head>" +
            "    <meta charset=\"UTF-8\">" +
            "    <meta name=\"viewport\"" +
            "          content=\"initial-scale=1.0, user-scalable=no, maximum-scale=1\">" +
            "<script src=\"https://api-maps.yandex.ru/2.1/?lang=ru_RU\" type=\"text/javascript\">" +
            "    </script>";

    private String generateJsCode(RouteWithWaypoints route, Location location) {
        String code = "<script type=\"text/javascript\">" +
                "        ymaps.ready(init);" +
                "        function init(){ " +
                "            var routeMap, " +
                "                currentLocationMark;" +
                "            routeMap = new ymaps.Map(\"map\", {" +
                "                center: " + parseLocation(location) + "," +
                "                zoom: 7" +
                "            });" + generatePlacemarksCode(route) +
                "            currentLocationMark = new ymaps.Placemark(" + parseLocation(location) + ",{" +
                "                balloonContent: 'Вы здесь'" +
                "            });" +
                "            routeMap.geoObjects.add(currentLocationMark);" + generteRouteCode(route) +
                "        }" +
                "    </script>" +
                "</head>" +
                "<body style=\"margin: 0px\">" +
                "    <div id=\"map\" style=\"width: 100%; height: 560px\"></div>" +
                "</body>";

        return code;
    }

    private String parseLocation(Location location) {
        return "[" + location.getLongitude() + "," + location.getLatitude() + "]";
    }

    private String generatePlacemarksCode(RouteWithWaypoints route) {
        String code = "";
        List<Waypoint> waypoints = route.getWaypoints();
        for (Waypoint w:
                 waypoints) {
            code += "routeMap.geoObjects.add(" +
                    "new ymaps.Placemark(\"" + w.getPointAddress() + "\"" + ",{" +
                    "}));";
        }
        return code;
    }

    private String generteRouteCode(RouteWithWaypoints route) {
        String code = "ymaps.route(" + getAddressesString(route.getWaypoints()) + ").then(" +
                "    function (route) {" +
                "        routeMap.geoObjects.add(route);" +
                "    }," +
                "    function (error) {" +
                "        alert('Возникла ошибка: ' + error.message);" +
                "    }" +
                ");";
        return code;
    }

    private String getAddressesString(List<Waypoint> waypoints) {
        String code = "[";
        if (waypoints.size() > 0) {
            for (Waypoint w :
                    waypoints) {
                code += "'" + w.getPointAddress() + "',";
            }
            code = code.substring(0, code.length()-1);
        }
        return code + "]";
    }

    @Override
    public String getMapWebViewPage(RouteWithWaypoints route, Location location) {
        return htmlPage + generateJsCode(route, location);
    }
}
