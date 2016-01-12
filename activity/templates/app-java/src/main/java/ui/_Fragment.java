package <%= appPackage %>.ui.<%= activityPackageName %>;

import <%= appPackage %>.di.components.<%= activityName %>Component;
import <%= appPackage %>.ui.base.BaseFragment;
import <%= appPackage %>.ui.base.EmptyPresenter;
import <%= appPackage %>.R;

public class <%= activityName %>Fragment extends BaseFragment<EmptyPresenter> {

  @Override
  protected void inject() {
    getComponent(<%= activityName %>Component.class).inject(this);
  }

  @Override
  protected int getLayoutResource() {
    return R.layout.fragment_<%= activityName.toLowerCase() %>;
  }
}
