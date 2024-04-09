package com.midterm.quocanle;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.midterm.quocanle.DAO.QuestionDAO;
import com.midterm.quocanle.databaseUtil.AppDatabase;
import com.midterm.quocanle.databinding.ActivityMainBinding;
import com.midterm.quocanle.model.Question;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    static MainActivity instance;
    ArrayList<Question> questionList;
    ArrayList<Question> userAnswerList;
    AppDatabase appDatabase;
    QuestionDAO questionDAO;
    int indexQuestion;

    public MainActivity() {
        instance = this;
    }

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        appDatabase = AppDatabase.getInstance(getApplicationContext());
        questionDAO = appDatabase.questionDAO();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        add question to database
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                questionDAO.insertAll(new Question("Hanoi is the capital of Vietnam?", "True"));
//                questionDAO.insertAll(new Question("Hanoi is the capital of China?", "False"));
//                questionDAO.insertAll(new Question("Hanoi is the capital of Japan?", "False"));
//                questionDAO.insertAll(new Question("Hanoi is the capital of Korea?", "False"));
//                questionDAO.insertAll(new Question("Hanoi is the capital of Laos?", "False"));
//                questionDAO.insertAll(new Question("Hanoi is the capital of Thailand?", "False"));
//                questionDAO.insertAll(new Question("Hanoi is the capital of Cambodia?", "False"));
//            }
//        });

        // get all question from database
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                questionList = (ArrayList<Question>) questionDAO.getAll();

                userAnswerList = new ArrayList<>(questionList);
                for (Question question : userAnswerList) {
                    question.setAnswer(null);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        indexQuestion = 0;
                        binding.textView.setText(userAnswerList.get(indexQuestion).getQuestion());
                    }
                });
            }
        });

        binding.btnTrue.setBackgroundColor(getResources().getColor(R.color.blue));
        binding.btnFalse.setBackgroundColor(getResources().getColor(R.color.blue));

//        indexQuestion = 0;
//        binding.textView.setText(userAnswerList.get(indexQuestion).getQuestion());
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indexQuestion > 0) {
                    indexQuestion--;
                    binding.textView.setText(userAnswerList.get(indexQuestion).getQuestion());
                    checkButton();
                }
            }
        });
        binding.btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indexQuestion < userAnswerList.size() - 1) {
                    indexQuestion++;
                    binding.textView.setText(userAnswerList.get(indexQuestion).getQuestion());
                    checkButton();
                }
            }
        });

        binding.btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswerList.get(indexQuestion).setAnswer("True");
                checkButton();
            }
        });
        binding.btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswerList.get(indexQuestion).setAnswer("False");
                checkButton();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });


    }

    void checkButton() {
        String userAnswer = userAnswerList.get(indexQuestion).getAnswer();
        if (userAnswer != null) {
            if (userAnswer.equals("True")) {
                binding.btnTrue.setBackgroundColor(getResources().getColor(R.color.green));
                binding.btnFalse.setBackgroundColor(getResources().getColor(R.color.blue));
            } else {
                binding.btnTrue.setBackgroundColor(getResources().getColor(R.color.blue));
                binding.btnFalse.setBackgroundColor(getResources().getColor(R.color.green));
            }
        } else {
            binding.btnTrue.setBackgroundColor(getResources().getColor(R.color.blue));
            binding.btnFalse.setBackgroundColor(getResources().getColor(R.color.blue));
        }
    }
}