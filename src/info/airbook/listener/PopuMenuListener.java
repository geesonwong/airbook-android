package info.airbook.listener;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class PopuMenuListener implements OnItemClickListener {

	private Context context;

	public PopuMenuListener(Context context) {
		this.context = context;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			check();
			break;
		case 1:
			call();
			break;
		case 2:
			delete();
			break;

		default:
			break;
		}
	}

	private void check() {
		Toast.makeText(context, "查看", Toast.LENGTH_LONG).show();
	}

	private void delete() {
		Toast.makeText(context, "删除", Toast.LENGTH_LONG).show();
	}

	private void call() {
		Toast.makeText(context, "拨打电话", Toast.LENGTH_LONG).show();
	}
}
