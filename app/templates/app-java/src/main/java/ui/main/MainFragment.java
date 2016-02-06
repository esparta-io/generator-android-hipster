package <%= appPackage %>.ui.main;

import <%= appPackage %>.di.components.MainComponent;
import <%= appPackage %>.ui.base.BaseFragment;
import <%= appPackage %>.ui.base.EmptyPresenter;
import <%= appPackage %>.R;

public class MainFragment extends BaseFragment<EmptyPresenter> {

    @Override
    protected void inject() {
        getComponent(MainComponent.class).inject(this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main;
    }

    @Override
    protected EmptyPresenter getPresenter() {
        return new EmptyPresenter();
    }

}
