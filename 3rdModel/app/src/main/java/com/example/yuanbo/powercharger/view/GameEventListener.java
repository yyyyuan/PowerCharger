package com.example.yuanbo.powercharger.view;

/**
 * Created by lezhu on 12/1/2017.
 */

public interface GameEventListener {
        // called when the user presses the `Easy` or `Okay` button; will pass in which via `hardMode`
        void onStartGameRequested(boolean hardMode);

        // called when the user presses the `Show Achievements` button
        void onShowAchievementsRequested();

        // called when the user presses the `Show Leaderboards` button
        void onShowLeaderboardsRequested();

        // called when the user presses the `Sign In` button
        void onSignInButtonClicked();

        // called when the user presses the `Sign Out` button
        void onSignOutButtonClicked();

        void onUpdateScore(int score);
}
