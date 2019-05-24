package com.sabirovfarit.android.rx.LearningWords;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sabirovfarit.android.rx.AddingNewListFragment;
import com.sabirovfarit.android.rx.LearningFragment;
import com.sabirovfarit.android.rx.SingleFragmentActivity;

public class LearningWordsActivity extends SingleFragmentActivity {

    public static final String LEARNING_WORDS_ACTIVITY_KEY = "LearningWordsActivity key";

    public static Intent newIntent(Context context, long id) {
        Intent intent = new Intent(context, LearningWordsActivity.class);
        intent.putExtra(LEARNING_WORDS_ACTIVITY_KEY,id);
        return intent;
    }

    @Override
    protected Fragment createFragment()  {
        return AllWordsLearningWordsFragment.newInstance(getIntent().getLongExtra(LEARNING_WORDS_ACTIVITY_KEY,0L));
    }

}
