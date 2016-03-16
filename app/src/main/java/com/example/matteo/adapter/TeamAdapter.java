package com.example.matteo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.matteo.model.Team;
import com.example.matteo.prova_2.R;

import java.util.List;

/**
 * Created by matteo on 07/03/2016.
 */
public class TeamAdapter extends BaseAdapter {

    private List<Team> teamList = null;

    public TeamAdapter(List<Team> teamList) {
        this.teamList = teamList;
    }

    @Override
    public int getCount() {
        return teamList.size();
    }

    @Override
    public Team getItem(int position) {
        return teamList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return teamList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater =
                (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.layout_teams,null);
        }
        Team team = getItem(position);

        TextView textPosition = (TextView) convertView.findViewById(R.id.position);
        textPosition.setText(String.valueOf(position+1));

        TextView textName = (TextView) convertView.findViewById(R.id.team_name);
        textName.setText(team.getName());

        TextView textPoints = (TextView) convertView.findViewById(R.id.team_points);
        textPoints.setText(String.valueOf(team.getPoints()));

        return convertView;
    }
}
