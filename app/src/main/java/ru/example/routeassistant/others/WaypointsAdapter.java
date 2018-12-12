package ru.kenguru.driverassistant.others;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.kenguru.driverassistant.DAL.entities.Waypoint;
import ru.kenguru.driverassistant.DAL.entities.WaypointStatus;
import ru.kenguru.driverassistant.DAL.entities.WaypointType;
import ru.kenguru.driverassistant.R;

public class WaypointsAdapter extends RecyclerView.Adapter<WaypointsAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Waypoint> waypoints;
    private Context context;

    public WaypointsAdapter(Context context, List<Waypoint> waypoints) {
        this.context = context;
        this.waypoints = waypoints;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.waypoints_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Waypoint waypoint = waypoints.get(position);

        String number = "" + waypoint.getSequenceNumber();
        String address = waypoint.getPointAddress();
        String type = WaypointType.getWaypointTypeById(waypoint.getTypeId()).getName();
        String cargo = waypoint.getCargo();
        String status = WaypointStatus.getWaypointStatusById(waypoint.getStatusId()).getName();

        holder.numberTw.setText(number);
        holder.addressTw.setText(address);
        holder.typeTw.setText(type);
        holder.cargoTw.setText(cargo);
        holder.statusTw.setText(status);
    }

    @Override
    public int getItemCount() {
        return waypoints.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView numberTw;
        final TextView addressTw;
        final TextView typeTw;
        final TextView cargoTw;
        final TextView statusTw;
        ViewHolder(View view){
            super(view);
            numberTw = (TextView) view.findViewById(R.id.numberTw);
            addressTw = (TextView) view.findViewById(R.id.addressTw);
            typeTw = (TextView) view.findViewById(R.id.typeTw);
            cargoTw = (TextView) view.findViewById(R.id.cargoTw);
            statusTw = (TextView) view.findViewById(R.id.statusTw);
        }
    }
}
