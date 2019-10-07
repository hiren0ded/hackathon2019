package com.example.demo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SellerFragement.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SellerFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellerFragement extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView listView;


    public SellerFragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SellerFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static SellerFragement newInstance(String param1, String param2) {
        SellerFragement fragment = new SellerFragement();
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_seller_fragement, container, false);

        List<TableClass> data = new ArrayList<>();
        data.add(new TableClass("Title1","1"));
        data.add(new TableClass("Title1","2"));
        data.add(new TableClass("Title1","3"));
        data.add(new TableClass("Title1","4"));

        //String[] maintitle = {"Title1","Title2","Title3","Title4","Title5"};
        //String[] subtitle = {"1","2","3","4","5"};

        listView = (ListView) rootView.findViewById(R.id.seller_listView);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1
        //, subtitle);
        MyAdapter adapter=new MyAdapter(this.getActivity(), R.layout.myxml ,data);
        listView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Sellet Activity...");
    }


}
