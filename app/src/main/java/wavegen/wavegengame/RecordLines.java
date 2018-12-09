package wavegen.wavegengame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

public class RecordLines extends View
{
    Paint paint = new Paint();
    int black, green, gold;

    private int wavescompleted = 0;

    public void setWavesCompleted(int wavescompleted){ //recieves the wavescompleted (from MainActivity)
        this.wavescompleted = wavescompleted;
    }

    public RecordLines(Context context)
    {
        super(context);
        init(context, null);
    }
    public RecordLines(Context context,AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }
    public RecordLines(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    public RecordLines(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet set){
        //set colors
        black = ContextCompat.getColor(context,R.color.colorPrimaryDark);
        green = ContextCompat.getColor(context,R.color.colorPrimary);
        gold = ContextCompat.getColor(context,R.color.colorAccent);
        //initial settings for paint
        paint.setColor(black);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        int x = getWidth();
        int y = getHeight();
        int radius;
        int spaceforlines = y/2 - x/2; //calculates space for lines an line spacing based on screen size
        int linespacing = spaceforlines/64;
        for(int i = 0; i < 64; i++){ //gold line shows current wave%64, green lines count how many times 64 waves have been completed, black lines are default
            if(i == wavescompleted%64){
                paint.setColor(gold);
            }
            else if(i < wavescompleted/64){
                paint.setColor(green);
            }
            else{
                paint.setColor(black);
            }
            radius = x/2 + 5 + i*linespacing; //radii of lines start from center and move outwards
            canvas.drawCircle(x / 2, y / 2, radius, paint); //draws circle with set color and radius
        }
    }
}