package com.example.michael.cal;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class CalNetworkFragment extends PlaceholderFragment implements AdapterView.OnItemClickListener{

    Activity activity;
    private static final String ARG_SECTION_NUMBER = "section_number";

    Button refreshButton;
    Button newProjectButton;

    ListView mListView;

    calProject[] projectList;
    String[] myprojectList = {"A", "b", "c"};
    String[] mydescList = {"Manager", "Client", "Peon"};
    public static CalNetworkFragment newInstance(int sectionNumber) {
        CalNetworkFragment fragment = new CalNetworkFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        this.activity = activity;
    }

    public CalNetworkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cal_network, container, false);

        refreshButton = (Button) view.findViewById(R.id.refresh_button);
        newProjectButton= (Button) view.findViewById(R.id.new_project_button);

        mListView = (ListView) view.findViewById(R.id.projectListView);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               clickRefresh(view);
            }
        });

        newProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickNewProject(view);
            }
        });

        myArrayAdapter adapter = new myArrayAdapter(getActivity(), myprojectList, mydescList );
        mListView.setAdapter(adapter);

        return view;
    }


    public void clickRefresh(View v){
        Log.d("CalNetworkFragment", "Refreshing Cal NSD List");
    }

    public void clickNewProject(View v){
        Log.d("CalNetworkFragment", "Creating a new project");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView temp = (TextView) view;
        TextView dst = (TextView) activity.findViewById(R.id.currentProject);
        dst.setText((temp).getText());
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
        public void onFragmentInteraction(Uri uri);
    }

    class myArrayAdapter extends ArrayAdapter<String>
    {
        Context context;
        String[] desc;
        String[] titles;
        myArrayAdapter(Context c, String[] titles, String[] descriptions) {

            super(c, R.layout.project_list_entry, R.id.firstLine, titles);
            this.context = c;
            this.desc = descriptions;
            this.titles = titles;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.project_list_entry, parent, false);

            TextView projectLabel = (TextView) row.findViewById(R.id.firstLine);
            TextView projectDescr = (TextView) row.findViewById(R.id.secondLine);

            projectDescr.setText(desc[position]);
            projectLabel.setText(titles[position]);

            return row; //super.getView(position, convertView, parent);
        }
    }
}
