package io.phasetwo.keycloak.themes.theme;

import com.google.common.collect.ImmutableSet;
import lombok.extern.jbosslog.JBossLog;
import org.keycloak.theme.Theme;
import org.keycloak.theme.ThemeProvider;
import org.keycloak.models.KeycloakSession;
import java.io.File;
import java.util.Set;
import java.io.IOException;

/** */
@JBossLog
public class AttributeThemeProvider implements ThemeProvider {

  private final KeycloakSession session;
  private final File tmpdir;
  
  public AttributeThemeProvider(KeycloakSession session, File tmpdir) {
    this.session = session;
    this.tmpdir = tmpdir;
  }

  @Override
  public int getProviderPriority() {
    return 200; // what does this do?
  }

  @Override
  public Theme getTheme(String name, Theme.Type type) throws IOException {
    if (!hasTheme(name, type)) return null;
    return new AttributeTheme(session, tmpdir, name, type);
  }

  public static final String ATTRIBUTE_THEME_NAME = "attributes";
  public static final Set<String> ATTRIBUTE_NAME_SET = ImmutableSet.of(ATTRIBUTE_THEME_NAME);
      
  @Override
  public Set<String> nameSet(Theme.Type type) {
    if (type == Theme.Type.EMAIL) return ATTRIBUTE_NAME_SET;
    else return ImmutableSet.of();
  }

  @Override
  public boolean hasTheme(String name, Theme.Type type) {
    return (type == Theme.Type.EMAIL && ATTRIBUTE_NAME_SET.contains(name.toLowerCase()));
  }

  @Override
  public void close() {
  }
}
