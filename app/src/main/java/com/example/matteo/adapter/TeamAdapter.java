package com.example.matteo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.matteo.model.Team;
import com.example.matteo.prova_2.R;

import java.util.Collections;
import java.util.Comparator;
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
        final Team team = getItem(position);

        TextView textPosition = (TextView) convertView.findViewById(R.id.position);
        textPosition.setText(String.valueOf(position + 1));

        TextView textName = (TextView) convertView.findViewById(R.id.team_name);
        textName.setText(team.getName());

        final TextView textPoints = (TextView) convertView.findViewById(R.id.team_points);
        textPoints.setText(String.valueOf(team.getPoints()));

        ImageButton plus = (ImageButton) convertView.findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = Integer.valueOf(textPoints.getText().toString()) + 1;
                textPoints.setText(String.valueOf(p));
                team.setPoints(p);
                notifyDataSetChanged();
            }

        });

        ImageButton minus = (ImageButton) convertView.findViewById(R.id.minus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textPoints.getText().toString().compareTo("0") != 0) {
                    int m = Integer.valueOf(textPoints.getText().toString()) - 1;
                    textPoints.setText(String.valueOf(m));
                    team.setPoints(m);
                    notifyDataSetChanged();
                }

            }
        });

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {

        Collections.sort(teamList, new Comparator<Team>() {
            @Override
            public int compare(Team lhs, Team rhs) {
                return rhs.getPoints() - lhs.getPoints();
            }
        });

        super.notifyDataSetChanged();
    }

}
