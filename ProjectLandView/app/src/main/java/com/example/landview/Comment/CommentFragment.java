package com.example.landview.Comment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.landview.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CommentFragment extends Fragment {
    private static final String TYPE = "type";
    private static final String PLACE_ID ="placeId";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String type;
    private String placeId;

    private ArrayList<Comment> comments;
    private CommentAdapter commentAdapter;
    CollectionReference collectionRef;

    public CommentFragment() {
        // Required empty public constructor
    }


    public static CommentFragment newInstance(String type, String placeId) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();

        args.putString(TYPE, type);
        args.putString(PLACE_ID, placeId);
        fragment.setArguments(args);
        return fragment;
    }



    private Button btnReview;
    private RecyclerView rcvComment;
    private TextView tvNoCmt;

    private void initUi(View view){
        btnReview = view.findViewById(R.id.btn_review);
        rcvComment = view.findViewById(R.id.rcv_comment);
        tvNoCmt = view.findViewById(R.id.tv_no_comments);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(TYPE);
            placeId = getArguments().getString(PLACE_ID);
            createCollectionRef();
        }
    }

    // T???o s???n m???t collection reference
    private void createCollectionRef(){
        switch (type){
            case "landscape":
                collectionRef = db.collection("landscapes").document(placeId).collection("review");
                break;
            case "hotel":
                collectionRef  = db.collection("hotels").document(placeId).collection("review");
                break;
            case "restaurant":
                collectionRef  = db.collection("restaurants").document(placeId).collection("review");
                break;
        }
    }

    // H??m ch??nh ??? ????y n??
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        initUi(view);

        // M??? write comment activity
        btnReview.setOnClickListener(btnReviewClickListener);

        // Update c??c comment c???a ng?????ng d??ng
        rcvComment.setLayoutManager(new LinearLayoutManager(getContext()));
        comments = new ArrayList<>();

        getComments();

        return view;
    }


    private View.OnClickListener btnReviewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(), WriteCommentActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("type", type);
            bundle.putString("placeId", placeId);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };


    private void getCommentData(){
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(!task.getResult().isEmpty()){

                        commentAdapter = new CommentAdapter(comments);
                        for(DocumentSnapshot document : task.getResult()){
                            Comment comment = document.toObject(Comment.class);
                            comments.add(comment);
                        }
                        rcvComment.setAdapter(commentAdapter);
                    } else {
                        rcvComment.setVisibility(View.GONE);
                        tvNoCmt.setVisibility(View.VISIBLE);
                    }
                    commentChange();
                }
            }
        });
    }

    /*
    *  addSnapshotListener: call back n??y s??? ??c g???i m???t l???n khi t???o activity ho???c fragment, v?? s??? ??c g???i l???i
    *  khi document trong collection thay ?????i. V?? d???: c?? document m???i ??c th??m v??o collection th?? h??m n??y s???
    *  ??c g???i l???i
    * */
    private void getComments(){
        collectionRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException error) {
                if (error == null){ // N???u ko c?? l???i trong qu?? tr??nh l???y d??? li???u t??? firebase

                    comments = new ArrayList<>(); // t???o l???i ArrayList;
                    commentAdapter = new CommentAdapter(comments);

                    if(querySnapshot.size() == 0){ // N???u kh??ng c?? comment n??o
                        tvNoCmt.setVisibility(View.VISIBLE); // hi???n th??ng b??o ko c?? cmt n??o
                        rcvComment.setVisibility(View.GONE); // ???n recyclerview hi???n comment

                    } else { // N???u c?? comment
                        for(DocumentSnapshot document : querySnapshot){
                            Comment comment = document.toObject(Comment.class);
                            comments.add(comment);
                        }
                        rcvComment.setAdapter(commentAdapter);
                        tvNoCmt.setVisibility(View.GONE); // ???N th??ng b??o ko c?? cmt n??o
                        rcvComment.setVisibility(View.VISIBLE); // Hi???n recyclerview hi???n comment
                    }

                } if( error != null){ // N???u x???y ra l???i trong qu?? tr??nh l???y d??? li???u
                    tvNoCmt.setVisibility(View.VISIBLE);
                    rcvComment.setVisibility(View.GONE);
                }
            }
        });
    }

    private void commentChange(){
        collectionRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null){
                    comments = new ArrayList<>();
                    commentAdapter = new CommentAdapter(comments);
                    for(DocumentSnapshot document : value){
                        Comment comment = document.toObject(Comment.class);
                        comments.add(comment);

                    }
                    rcvComment.setAdapter(commentAdapter);
                    tvNoCmt.setVisibility(View.GONE);
                    rcvComment.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}