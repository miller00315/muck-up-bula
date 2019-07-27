package br.com.miller.muckup.utils.alerts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

import br.com.miller.muckup.R;
import br.com.miller.muckup.utils.image.ImageUtils;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class ImageDialogFragment extends DialogFragment {

    private static final String DIALOG_TAG = ImageDialogFragment.class.getSimpleName();

    private Intent intent;

    private ImageDialogFragmentListener imageDialogFragmentListener;

    public static ImageDialogFragment newInstance(Bundle bundle){

        ImageDialogFragment imageDialogFragment = new ImageDialogFragment();

        imageDialogFragment.setArguments(bundle);

        return imageDialogFragment;
    }

    public void setListenerIntent(ImageDialogFragmentListener imageDialogFragmentListener, Intent intent){
        this.intent = intent;
        this.imageDialogFragmentListener = imageDialogFragmentListener;
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (intent == null) {

            imageDialogFragmentListener.onImageDialogFragmentListener (null, RESULT_CANCELED);
            dismiss();
        }

        View view = inflater.inflate(R.layout.layout_alert_image, container, false);

        final ImageView imageView = view.findViewById(R.id.image_memory);

        ImageUtils.setImageFromMemory(intent, imageView);

        Button confirm = view.findViewById(R.id.confirm);
        Button cancel = view.findViewById(R.id.cancel);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageDialogFragmentListener.onImageDialogFragmentListener(ImageUtils.getImageUser(imageView), RESULT_OK);

                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;

    }


        public void openDialog(FragmentManager fm) { if (fm.findFragmentByTag(DIALOG_TAG) == null) { show(fm, DIALOG_TAG); } }

        public interface ImageDialogFragmentListener{ void onImageDialogFragmentListener(Bitmap bitmap, int reult); }
}
