package ru.kenguru.driverassistant.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import ru.kenguru.driverassistant.DAL.entities.RouteWithWaypoints;
import ru.kenguru.driverassistant.DAL.entities.Waypoint;
import ru.kenguru.driverassistant.DAL.entities.WaypointType;
import ru.kenguru.driverassistant.DriverAssistantApp;
import ru.kenguru.driverassistant.MainActivity;
import ru.kenguru.driverassistant.R;
import ru.kenguru.driverassistant.controllers.IRouteController;
import ru.kenguru.driverassistant.controllers.RouteController;
import ru.kenguru.driverassistant.others.DateController;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RouteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RouteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RouteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private IRouteController routeController;

    private TextView routeNumberTw;
    private TextView routeStartTw;
    private TextView routeDistanceTw;
    private TextView routeFinishTw;
    private TextView routeCurrentTimeTw;
    private TextView routeWaypointAddressTw;
    private TextView routeWaypointActionTw;
    private TextView routeWaypointCargoTw;
    private TextView routeWaypointFinishPlanTw;

    private Timer timer = null;
    private RouteTimerTask timerTask = null;

    class RouteTimerTask extends TimerTask {

        @Override
        public void run() {

            final DateController dateController = new DateController();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Calendar calendar = Calendar.getInstance();
                    Date dateStartFact = dateController.parseString(routeController.getCurrentRoute().getRoute().getStartTimeFact());
                    long difTime = dateController.getDifDate(calendar.getTime(), dateStartFact);
                    String strTime = getContext().getResources().getString(R.string.route_info_current_time) + ": " + dateController.parseDifTime(difTime);
                    routeCurrentTimeTw.setText(strTime);
                }
            });
        }
    }

    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timerTask = new RouteTimerTask();
        timer.schedule(timerTask, 0, 60000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public RouteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RouteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RouteFragment newInstance(String param1, String param2) {
        RouteFragment fragment = new RouteFragment();
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

        // Инициализация контроллера
        routeController = DriverAssistantApp.getRouteController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_route, container, false);

        routeNumberTw = (TextView) view.findViewById(R.id.routeNumberTw);
        routeStartTw = (TextView) view.findViewById(R.id.routeStartFactTw);
        routeDistanceTw = (TextView) view.findViewById(R.id.routeDistanceTw);
        routeFinishTw = (TextView) view.findViewById(R.id.routeFinishPlanTw);
        routeCurrentTimeTw = (TextView) view.findViewById(R.id.routeCurrentTimeTw);
        routeWaypointAddressTw = (TextView) view.findViewById(R.id.routeWaypointAddressTw);
        routeWaypointActionTw = (TextView) view.findViewById(R.id.routeWaypointActionTw);
        routeWaypointCargoTw = (TextView) view.findViewById(R.id.routeWaypointCargoTw);
        routeWaypointFinishPlanTw = (TextView) view.findViewById(R.id.routeWaypointFinishPlanTw);

        int state = routeController.getStatment();
        if (state != 1 && state != 2) startTimer();

        initTextViews(routeController.getCurrentRoute());
        return view;
    }

    @Override
    public void onPause() {
        stopTimer();
        super.onPause();
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

    public void initTextViews(RouteWithWaypoints route) {
        Waypoint waypoint = routeController.getCurrentWaypoint();
        String str = getResources().getString(R.string.route_info_number) + ": " + route.getRoute().getId();
        routeNumberTw.setText(str);
        String start = route.getRoute().getStartTimeFact();
        str = getResources().getString(R.string.route_info_start) + ": " + (start == null ? "н/д" : start);
        routeStartTw.setText(str);
        str = getResources().getString(R.string.route_info_distance) + ": " + route.getRoute().getTotalDistance()
                + " " + getResources().getString(R.string.kilometres);
        routeDistanceTw.setText(str);
        str = getResources().getString(R.string.route_info_finish) + ": " + route.getRoute().getFinishTimePlan();
        routeFinishTw.setText(str);
        str = getResources().getString(R.string.route_info_current_time) + ": 0 Часов 0 Минут";
        routeCurrentTimeTw.setText(str);
        if (waypoint == null) {
            str = getResources().getString(R.string.route_info_waypoint_address) + ": " + route.getRoute().getFinishPointAddress();
            routeWaypointAddressTw.setText(str);
            str = getResources().getString(R.string.route_info_waypoint_type) + ": " + WaypointType.getWaypointTypeById(3).getName();
            routeWaypointActionTw.setText(str);
            str = getResources().getString(R.string.route_info_waypoint_cargo) + ": Отсутствует";
            routeWaypointCargoTw.setText(str);
            str = getResources().getString(R.string.route_info_waypoint_finish_plan) + ": " + route.getRoute().getFinishTimePlan();
            routeWaypointFinishPlanTw.setText(str);
        }
        else {
            str = getResources().getString(R.string.route_info_waypoint_address) + ": " + waypoint.getPointAddress();
            routeWaypointAddressTw.setText(str);
            str = getResources().getString(R.string.route_info_waypoint_type) + ": " + WaypointType.getWaypointTypeById(waypoint.getTypeId()).getName();
            routeWaypointActionTw.setText(str);
            str = getResources().getString(R.string.route_info_waypoint_cargo) + ": " + waypoint.getCargo();
            routeWaypointCargoTw.setText(str);
            str = getResources().getString(R.string.route_info_waypoint_finish_plan) + ": " + waypoint.getLeaveTimePlan();
            routeWaypointFinishPlanTw.setText(str);
        }
    }
}
