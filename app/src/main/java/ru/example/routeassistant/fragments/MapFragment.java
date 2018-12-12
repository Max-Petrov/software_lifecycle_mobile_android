package ru.kenguru.driverassistant.fragments;

import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.driving.DrivingOptions;
import com.yandex.mapkit.driving.DrivingRoute;
import com.yandex.mapkit.driving.DrivingRouter;
import com.yandex.mapkit.driving.DrivingSession;
import com.yandex.mapkit.driving.RequestPoint;
import com.yandex.mapkit.driving.RequestPointType;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;

import java.util.ArrayList;
import java.util.List;

import ru.kenguru.driverassistant.DAL.entities.Location;
import ru.kenguru.driverassistant.DAL.entities.RouteWithWaypoints;
import ru.kenguru.driverassistant.DAL.entities.Waypoint;
import ru.kenguru.driverassistant.DriverAssistantApp;
import ru.kenguru.driverassistant.R;
import ru.kenguru.driverassistant.controllers.ILocationController;
import ru.kenguru.driverassistant.controllers.IRouteController;
import ru.kenguru.driverassistant.controllers.IWaypointController;
import ru.kenguru.driverassistant.controllers.LocationController;
import ru.kenguru.driverassistant.controllers.RouteController;
import ru.kenguru.driverassistant.controllers.WaypointController;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements DrivingSession.DrivingRouteListener, UserLocationObjectListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private MapView mapView = null;
    private MapObjectCollection mapObjects = null;
    private DrivingRouter drivingRouter = null;
    private DrivingSession drivingSession = null;
    private UserLocationLayer locationLayer = null;

    private IRouteController routeController = null;
    private ILocationController locationController = null;

    private ArrayList<RequestPoint> requestPoints = null;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        routeController = RouteController.getInstance();
        locationController = LocationController.getInstance();
        MapKitFactory.setApiKey(getResources().getString(R.string.mapkit_key));
        MapKitFactory.initialize(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) view.findViewById(R.id.mapview);
        initMap();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    private ArrayList<RequestPoint> createPoints(RouteWithWaypoints route) {
        if (DriverAssistantApp.getRouteController().getCurrentRoute() != null) {
            IWaypointController waypointController = WaypointController.getInstance();
            ArrayList<RequestPoint> requestPoints = new ArrayList<>();
            requestPoints.add(new RequestPoint(
                    new Point(route.getRoute().getStartPointLat(), route.getRoute().getStartPointLon()),
                    new ArrayList<Point>(), RequestPointType.WAYPOINT));

            List<Waypoint> waypoints = route.getWaypoints();

            for (Waypoint w :
                    waypoints) {
                requestPoints.add(new RequestPoint(
                        new Point(w.getPointLatitude(), w.getPointLongitude()),
                        new ArrayList<Point>(), RequestPointType.WAYPOINT));
            }

            requestPoints.add(new RequestPoint(
                    new Point(route.getRoute().getFinishPointLatitude(), route.getRoute().getFinishPointLongitude()),
                    new ArrayList<Point>(), RequestPointType.WAYPOINT));

            return requestPoints;
        }
        return null;
    }

    private void initMap() {
        RouteWithWaypoints route = routeController.getCurrentRoute();
        if (this.requestPoints == null) {
            this.requestPoints = createPoints(route);
        }

        mapView.getMap().getTrafficLayer().setTrafficVisible(true);

        locationLayer = mapView.getMap().getUserLocationLayer();
        locationLayer.setEnabled(true);
        locationLayer.setHeadingEnabled(true);
        locationLayer.setObjectListener(this);

        drivingRouter = MapKitFactory.getInstance().createDrivingRouter();
        mapObjects = null;
        mapObjects = mapView.getMap().getMapObjects().addCollection();

        if (route != null) {
            double centerLat = (requestPoints.get(0).getPoint().getLatitude()
                    + requestPoints.get(requestPoints.size() - 1).getPoint().getLatitude()) / 2;
            double centerLon = (requestPoints.get(0).getPoint().getLongitude()
                    + requestPoints.get(requestPoints.size() - 1).getPoint().getLongitude()) / 2;
            mapView.getMap().move(
                    new CameraPosition(new Point(centerLat, centerLon), 12.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 0),
                    null);

            showWaypoints(requestPoints);

//            Location location = locationController.getCurrentLocation();
//            if (location != null) {
//                showLocation(location);
//            }

            DrivingOptions options = new DrivingOptions();

            drivingSession = drivingRouter.requestRoutes(requestPoints, options, this);
        }
        else {
            mapView.getMap().move(
                    new CameraPosition(new Point(57.000348, 40.973921), 10.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 0),
                    null);
        }
    }

    private void showLocation(Location location) {
        PlacemarkMapObject mark = mapObjects.addPlacemark(new Point(location.getLatitude(), location.getLongitude()));
        mark.setIcon(ImageProvider.fromResource(getActivity(), R.drawable.user_arrow));
    }

    private void showWaypoints(ArrayList<RequestPoint> points) {
        for (RequestPoint p :
                points) {
            PlacemarkMapObject mark = mapObjects.addPlacemark(new Point(p.getPoint().getLatitude(), p.getPoint().getLongitude()));
            mark.setIcon(ImageProvider.fromResource(getActivity(), R.drawable.waypoint_icon_small));
        }

    }

    @Override
    public void onDrivingRoutes(List<DrivingRoute> routes) {
        DrivingRoute route = getOptimalRoute(routes);
        if (route != null) {
            mapObjects.addPolyline(route.getGeometry());
        }
    }

    private DrivingRoute getOptimalRoute(List<DrivingRoute> routes) {
        DrivingRoute minRoute = null;
        if (routes != null && routes.size() > 0) {
            minRoute = routes.get(0);
            double minTime = minRoute.getMetadata().getWeight().getTimeWithTraffic().getValue();

            for (DrivingRoute r :
                    routes) {
                double time = r.getMetadata().getWeight().getTimeWithTraffic().getValue();
                if (time <= minTime) {
                    if (time == minTime) {
                        if (r.getMetadata().getWeight().getDistance().getValue() < minRoute.getMetadata().getWeight().getDistance().getValue()) {
                            minRoute = r;
                        }
                    }
                    else {
                        minRoute = r;
                        minTime = time;
                    }
                }
            }
        }
        return minRoute;
    }

    @Override
    public void onDrivingRoutesError(Error error) {
        String errorMessage = getString(R.string.unknown_error_message);
        if (error instanceof RemoteError) {
            errorMessage = getString(R.string.remote_error_message);
        } else if (error instanceof NetworkError) {
            errorMessage = getString(R.string.network_error_message);
        }

        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onObjectAdded(UserLocationView userLocationView) {
        locationLayer.setAnchor(
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.5)),
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.83)));

        userLocationView.getPin().setIcon(ImageProvider.fromResource(
                getContext(), R.drawable.user_arrow));
        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                getContext(), R.drawable.user_arrow));
    }

    @Override
    public void onObjectRemoved(UserLocationView userLocationView) {

    }

    @Override
    public void onObjectUpdated(UserLocationView userLocationView, ObjectEvent objectEvent) {
        initMap();
    }
}
