package com.midterm.quocanle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.midterm.quocanle.databinding.ActivityDetailsBinding;
import com.midterm.quocanle.databinding.ActivityMainBinding;
import com.midterm.quocanle.model.Question;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    ArrayAdapter<Question> adapter;
    ArrayList<Question> questionList;
    ArrayList<Question> userAnswerList;
    ArrayList<Question> result;
    int score = 0;
    int numQuestion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        Intent intent = getIntent();

        questionList = MainActivity.getInstance().questionList;
        userAnswerList = MainActivity.getInstance().userAnswerList;
        numQuestion = userAnswerList.size();
        result = new ArrayList<>();
//        for (Question question : questionList) {
//            for (Question userAnswer : userAnswerList) {
//                if (question.getQuestion().equals(userAnswer.getQuestion())) {
//                    if (question.getAnswer().equals(userAnswer.getAnswer())) {
//                        result.add(new Question(question.getQuestion(), "Correct"));
//                        score++;
//                    } else {
//                        result.add(new Question(question.getQuestion(), "Wrong"));
//                    }
//                }
//            }
//        }
        for (int i = 0; i < questionList.size(); i++) {
            Question question = questionList.get(i);
            Question userAnswer = userAnswerList.get(i);

            if (question.getAnswer() != null && question.getAnswer().equals(userAnswer.getAnswer())) {
                result.add(new Question(question.getQuestion(), "Correct"));
                score++;
            } else {
                result.add(new Question(question.getQuestion(), "Wrong"));
            }
        }

        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new ArrayAdapter<Question>(DetailsActivity.this, android.R.layout.simple_list_item_1, result) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                Question question = result.get(position);
                textView.setText(question.getQuestion() + " - " + question.getAnswer());
                return textView;
            }
        };
        binding.lvDetails.setAdapter(adapter);

        binding.tvScore.setText(score + "/" + numQuestion);
    }
}