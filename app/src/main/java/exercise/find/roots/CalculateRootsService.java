package exercise.find.roots;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static java.lang.Math.ceil;
import static java.lang.Math.sqrt;

public class CalculateRootsService extends IntentService {
  public CalculateRootsService() {
    super("CalculateRootsService");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    if (intent == null) return;
    long numberToCalculateRootsFor = intent.getLongExtra("number_for_service", 0);
    Intent sendIntent = new Intent();
    long i = 2;
    long root1 = 0;
    long root2 = 0;
    long timeStartMs = System.currentTimeMillis();
    while (i < ceil(sqrt(numberToCalculateRootsFor)))
    {
      if (numberToCalculateRootsFor % i == 0)
      {
        root1 = i;
        root2 = numberToCalculateRootsFor/i;
        break;
      }
      long curTime = System.currentTimeMillis() - timeStartMs;
      if (curTime >= 20000)
      {
        sendIntent.setAction("stopped_calculations");
        sendIntent.putExtra("original_number", numberToCalculateRootsFor);
        sendIntent.putExtra("time_until_give_up_seconds", curTime/1000);
        sendBroadcast(sendIntent);
        return;
      }
      i++;
    }

    long time = System.currentTimeMillis() - timeStartMs;
    if (root1 == 0 && root2 == 0)
    {
      sendIntent.setAction("found_roots");
      sendIntent.putExtra("original_number", numberToCalculateRootsFor);
      sendIntent.putExtra("root1", 1);
      sendIntent.putExtra("root2", numberToCalculateRootsFor);
      sendIntent.putExtra("time_until_give_up_seconds", time/1000);
      sendBroadcast(sendIntent);
      return;
    }
    if (numberToCalculateRootsFor <= 0) {
      Log.e("CalculateRootsService", "can't calculate roots for non-positive input" + numberToCalculateRootsFor);
      return;
    }

    sendIntent.setAction("found_roots");
    sendIntent.putExtra("original_number", numberToCalculateRootsFor);
    sendIntent.putExtra("root1", root1);
    sendIntent.putExtra("root2", root2);
    sendIntent.putExtra("time_until_give_up_seconds", time/1000);
    sendBroadcast(sendIntent);
    /*
    TODO:
     calculate the roots.
     check the time (using `System.currentTimeMillis()`) and stop calculations if can't find an answer after 20 seconds
     upon success (found a root, or found that the input number is prime):
      send broadcast with action "found_roots" and with extras:
       - "original_number"(long)
       - "root1"(long)
       - "root2"(long)
     upon failure (giving up after 20 seconds without an answer):
      send broadcast with action "stopped_calculations" and with extras:
       - "original_number"(long)
       - "time_until_give_up_seconds"(long) the time we tried calculating

      examples:
       for input "33", roots are (3, 11)
       for input "30", roots can be (3, 10) or (2, 15) or other options
       for input "17", roots are (17, 1)
       for input "829851628752296034247307144300617649465159", after 20 seconds give up

     */
  }


}