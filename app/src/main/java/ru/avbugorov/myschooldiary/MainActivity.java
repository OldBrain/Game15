package ru.avbugorov.myschooldiary;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import java.util.*;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {
    private float volume;
    private SoundPool soundPool;
    public static final int SOUND_COUNT = 3;
    public int[] soundID = new int[SOUND_COUNT];
    public boolean loaded;
    public Context context = MainActivity.this;
    public AudioAttributes audioAttributes = new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build();
    SoundPool.Builder builder = new SoundPool.Builder();
    public static final int ROWS = 4;
    public static final int COLUMNS = 4;
    public static SquareButton[][] buttons = new SquareButton[ROWS][COLUMNS];
    public static ArrayList<SquareButton> listButton = new ArrayList<SquareButton>(16);
    public static int[][] coordinates = new int[ROWS][COLUMNS];
    public Point emptySpace = new Point();
    public AlertDialog.Builder alDialog;
    enum buttonColor { BLACK,BLUE,GREEN, WHITE, OLD }
    private boolean isNotStir=true;
    public SensorManager manager;
    public Sensor rotation_vector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();// Убрать ActionBar
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.one);
        setContentView(R.layout.mytable_layout_prog);
//        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
//        rotation_vector = manager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        initSound(1);
        generateArray();
        initButton();

        setStyleButtons(buttonColor.BLUE);

        Collections.shuffle(listButton);
        initTable();
        setListenersOnButtons();
        setLongClickListenersOnButtons();

           }

    private ActionBar getSupportActionBar() {
        return null;
    }


    private void initStir(int countStir) {

        isNotStir = false;
        for (int i = 0; i < countStir; i++) {

            for (int j = 0; j < countStir; j++) {
                Random random = new Random();
                int tmpI = random.nextInt(4);

                int tmpJ = random.nextInt(4);

                Point clickedPointStir =getClickedPoint(buttons[tmpI][tmpJ]);
                if (canMove(clickedPointStir)) {
                    buttons[tmpI][tmpJ].performClick();
                }

            }
        }
        isNotStir = true;
        soundPool.play(soundID[0], 1, 0, 0, 0, 2);
    }

    public void setStyleButtons(buttonColor color) {
        switch (color) {
            case GREEN:
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLUMNS; j++) {
                buttons[i][j].setBackgroundResource(R.drawable.mybuttonsgreen);
                buttons[i][j].setTextSize(40);
                    }
                }
                break;
            case BLUE:
                for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                buttons[i][j].setBackgroundResource(R.drawable.mybuttonsblue);
                buttons[i][j].setTextSize(40);
                 }
        }
                break;
            case WHITE:
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLUMNS; j++) {
                buttons[i][j].setBackgroundResource(R.drawable.mybuttonswhite);
                buttons[i][j].setTextSize(40);
                    }
                }
                break;
            case BLACK:
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLUMNS; j++) {
                buttons[i][j].setBackgroundResource(R.drawable.mybuttonsblask);
                buttons[i][j].setTextSize(40);
                    }
                }
                break;
            case OLD:
                break;


        }


    }

    private void setLongClickListenersOnButtons() {

        View.OnLongClickListener longClickButtons = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View longView) {
                new ColorAlertDialog(context);
                 initStir(100);
                return true;
            }
        };

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Button button = buttons[i][j];
                button.setOnLongClickListener(longClickButtons);
//                squareLayout.setOnLongClickListener(longClickSquareLayaut);
            }

        }

    }




    private void setListenersOnButtons() {
        OnClickListener listener = new OnClickListener() {
            public void onClick(View myView) {
                Button clickedButton = (Button) myView;
                Point clickedPoint = getClickedPoint(clickedButton);

                if (clickedPoint != null && canMove(clickedPoint)) {
                    if (isNotStir) {
                        playSound();
                    } else {

                    }
                    clickedButton.setVisibility(View.INVISIBLE);
                    String numberStr = clickedButton.getText().toString();
                    clickedButton.setText(" ");
                    Button button = buttons[emptySpace.x][emptySpace.y];
                    button.setVisibility(View.VISIBLE);
                    button.setText(numberStr);
                    emptySpace.x = clickedPoint.x;
                    emptySpace.y = clickedPoint.y;
                } else {
                    if (isNotStir) {
                        msg();
                    }

                }
            }
        };

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Button button = buttons[i][j];
                button.setOnClickListener(listener);
            }
        }

    }

    private void msg() {
       ImgMsg msg = new ImgMsg(this, "Это двигать нельзя!!!");
       soundPool.play(soundID[2], 1, 0, 0, 0, 2);
    }

    private void initButton() {

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                SquareButton button = new SquareButton(this);

                int tmp = new GetXY(i, j).num + 1;
                button.setId(tmp);
                button.setText("" + tmp);
                buttons[i][j] = button;
                listButton.add(buttons[i][j]);
                int number = coordinates[i][j];
                if (number > -1) {
                    button.setText("" + number);
                } else {
                    button.setText(" ");
                    button.setVisibility(View.INVISIBLE);
                }
            }
        }

    }

    private void initTable() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tlTable1);
        for (int i = 0; i < ROWS; i++) {
            TableRow tableRow = new TableRow(this);
            for (int j = 0; j < COLUMNS; j++) {
                int tmpIndex = new GetXY(i, j).num;
                tableRow.addView(buttons[i][j], j);
            }
            tableLayout.addView(tableRow, i);
        }
    }

    private boolean canMove(Point clicked) {
        if (clicked.equals(emptySpace)) {
            return false;
        }
        if (clicked.x == emptySpace.x) {
            int diff = Math.abs(clicked.y - emptySpace.y);
            if (diff == 1) {
                return true;
            }
        } else if (clicked.y == emptySpace.y) {
            int diff = Math.abs(clicked.x - emptySpace.x);
            if (diff == 1) {
                return true;
            }
        }
        return false;
    }

    private Point getClickedPoint(Button clickedButton) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (clickedButton == buttons[i][j]) {
                    Point point = new Point();
                    point.x = i;
                    point.y = j;
                    return point;
                }
            }
        }
        return null;
    }

    private void generateArray() {
        int k = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (k >= 16) {
                    emptySpace.x = i;
                    emptySpace.y = j;
                    coordinates[i][j] = -1;
                } else {
                    coordinates[i][j] = k;
//                    array[i][j] = i * 4 + j + 1;
                }
                k++;
            }
        }
    }


    public void initSound(int typeSound) {
//        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setAudioAttributes(audioAttributes).setMaxStreams(5);
        soundPool = builder.build();
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;

            }
        });


            soundID[1] = soundPool.load(this, R.raw.clic13, 1);

            soundID[2] = soundPool.load(this, R.raw.error, 1);
            soundID[0]=  soundPool.load(this, R.raw.stir, 1);


    }

    private void playSound() {
        soundPool.play(soundID[1], 1, 0, 0, 0, 2);
    }






}
