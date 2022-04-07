package com.example.note.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.note.R;
import com.example.note.data.Note;
import com.example.note.recycler.NoteAdapter;
import com.google.android.material.navigation.NavigationView;

public class NotesActivity extends AppCompatActivity implements NoteAdapter.onNoteClickListener {

    private FragmentManager manager;
    private Note note;
    private ActionBarDrawerToggle toggle;

    public static final String CHANNEL_ID = "NOTIFY CHANNEL ID";

    @Override
    public void onNoteClick(Note note) {
        this.note = note;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        toolbarInit();

        manager = getSupportFragmentManager();

        manager.setFragmentResultListener("TEST", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                note = (Note) result.getSerializable(Note.NOTE);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
        
        if (savedInstanceState == null) {
            manager.popBackStack();
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                manager
                        .beginTransaction()
                        .replace(R.id.landscape_notes_list_fragment_holder, new NotesListFragment())
                        .commit();

            } else {

                manager
                        .beginTransaction()
                        .replace(R.id.portrait_fragment_holder, new NotesListFragment())
                        .commit();

            }

        } else {

            this.note = (Note) savedInstanceState.getSerializable(Note.NOTE);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                manager
                        .beginTransaction()
                        .replace(R.id.landscape_notes_list_fragment_holder, new NotesListFragment())
                        .commit();
                if (note != null) {
                    manager
                            .beginTransaction()
                            .replace(R.id.landscape_notes_edit_fragment_holder, EditNoteFragment.getInstance(this.note))
                            .addToBackStack(null)
                            .commit();

                } else {

                    manager
                            .beginTransaction()
                            .replace(R.id.landscape_notes_edit_fragment_holder, new EditNoteFragment())
                            .addToBackStack(null)
                            .commit();

                }

            } else {
                if (note != null) {
                    manager
                            .beginTransaction()
                            .replace(R.id.portrait_fragment_holder, EditNoteFragment.getInstance(this.note))
                            .addToBackStack(null)
                            .commit();
                } else {
                    manager
                            .beginTransaction()
                            .replace(R.id.portrait_fragment_holder, new NotesListFragment())
                            .commit();
                }
            }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        String name = "notify";
        String description = "notify description";
        int channelImportance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, channelImportance);
        channel.setDescription(description);
        NotificationManagerCompat.from(this).createNotificationChannel(channel);
    }

    @Override
    public void onBackPressed() {

        if (manager.getBackStackEntryCount() == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Confirm")
                    .setMessage("Do you want Exit?!")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), "Bye", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setNeutralButton("Cancel", null)
                    .show();
        } else {
            super.onBackPressed();
        }

    }

    public void toolbarInit() {


        DrawerLayout drawerLayout = findViewById(R.id.drawer_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_menu_settings, R.string.drawer_menu_about);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();



        NavigationView navigationView = findViewById(R.id.drawer_menu_nav);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                manager.popBackStack();
                int id = item.getItemId();
                switch (id) {
                    case R.id.drawer_menu_settings:
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            manager
                                    .beginTransaction()
                                    .replace(R.id.landscape_notes_edit_fragment_holder, new SettingsFragment())
                                    .addToBackStack(null)
                                    .commit();
                        } else {
                            manager
                                    .beginTransaction()
                                    .replace(R.id.portrait_fragment_holder, new SettingsFragment())
                                    .addToBackStack(null)
                                    .commit();
                        }

                        drawerLayout.close();
                        return true;
                    case R.id.drawer_menu_about:
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            manager
                                    .beginTransaction()
                                    .replace(R.id.landscape_notes_edit_fragment_holder, new AboutFragment())
                                    .addToBackStack(null)
                                    .commit();
                        } else {
                            manager
                                    .beginTransaction()
                                    .replace(R.id.portrait_fragment_holder, new AboutFragment())
                                    .addToBackStack(null)
                                    .commit();
                        }
                        drawerLayout.close();
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_navigaton_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                manager
                        .beginTransaction()
                        .replace(R.id.landscape_notes_edit_fragment_holder, new EditNoteFragment())
                        .addToBackStack(null)
                        .commit();
            } else {
                manager
                        .beginTransaction()
                        .replace(R.id.portrait_fragment_holder, new EditNoteFragment())
                        .addToBackStack(null)
                        .commit();
            }


        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Note.NOTE, this.note);
    }
}