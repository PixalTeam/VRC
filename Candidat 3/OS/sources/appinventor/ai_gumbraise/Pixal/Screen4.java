package appinventor.ai_gumbraise.Pixal;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AppInventorCompatActivity;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalScrollArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.PasswordTextBox;
import com.google.appinventor.components.runtime.TextBox;
import com.google.appinventor.components.runtime.TinyDB;
import com.google.appinventor.components.runtime.Web;
import com.google.appinventor.components.runtime.errors.PermissionException;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.RetValManager;
import com.google.appinventor.components.runtime.util.RuntimeErrorAlert;
import com.google.youngandroid.runtime;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.Apply;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.kawa.reflect.SlotSet;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.lists.VoidConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lang.Promise;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.strings;
import kawa.standard.Scheme;

/* compiled from: Screen4.yail */
public class Screen4 extends Form implements Runnable {
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("Screen4").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("getMessage").readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve());
    static final SimpleSymbol Lit100 = ((SimpleSymbol) new SimpleSymbol(TinyDB.DEFAULT_NAMESPACE).readResolve());
    static final FString Lit101 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final FString Lit102 = new FString("com.google.appinventor.components.runtime.Web");
    static final FString Lit103 = new FString("com.google.appinventor.components.runtime.Web");
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol("get-simple-name").readResolve());
    static final SimpleSymbol Lit105 = ((SimpleSymbol) new SimpleSymbol("android-log-form").readResolve());
    static final SimpleSymbol Lit106 = ((SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve());
    static final SimpleSymbol Lit107 = ((SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve());
    static final SimpleSymbol Lit108 = ((SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve());
    static final SimpleSymbol Lit109 = ((SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve());
    static final IntNum Lit11;
    static final SimpleSymbol Lit110 = ((SimpleSymbol) new SimpleSymbol("add-to-events").readResolve());
    static final SimpleSymbol Lit111 = ((SimpleSymbol) new SimpleSymbol("add-to-components").readResolve());
    static final SimpleSymbol Lit112 = ((SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve());
    static final SimpleSymbol Lit113 = ((SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve());
    static final SimpleSymbol Lit114 = ((SimpleSymbol) new SimpleSymbol("send-error").readResolve());
    static final SimpleSymbol Lit115 = ((SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve());
    static final SimpleSymbol Lit116 = ((SimpleSymbol) new SimpleSymbol("dispatchGenericEvent").readResolve());
    static final SimpleSymbol Lit117 = ((SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("PrimaryColor").readResolve());
    static final IntNum Lit13;
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("PrimaryColorDark").readResolve());
    static final IntNum Lit15;
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("Scrollable").readResolve());
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve());
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("ShowListsAsJson").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("Sizing").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("Title").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("TitleVisible").readResolve());
    static final FString Lit22 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("D1").readResolve());
    static final IntNum Lit24;
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("Width").readResolve());
    static final IntNum Lit26 = IntNum.make(-2);
    static final FString Lit27 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit28 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("D2").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("g$TheURL").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("Height").readResolve());
    static final IntNum Lit31 = IntNum.make(50);
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol("Image").readResolve());
    static final FString Lit33 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit34 = PairWithPosition.make(Lit9, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen4.yail", 184398);
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("D2$Click").readResolve());
    static final SimpleSymbol Lit36 = ((SimpleSymbol) new SimpleSymbol("Click").readResolve());
    static final FString Lit37 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit38 = ((SimpleSymbol) new SimpleSymbol("D3").readResolve());
    static final IntNum Lit39;
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("g$TempURL").readResolve());
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("FontSize").readResolve());
    static final IntNum Lit41 = IntNum.make(25);
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("TextAlignment").readResolve());
    static final IntNum Lit44 = IntNum.make(2);
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("TextColor").readResolve());
    static final IntNum Lit46;
    static final FString Lit47 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit48 = PairWithPosition.make(Lit9, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen4.yail", 262222);
    static final SimpleSymbol Lit49 = ((SimpleSymbol) new SimpleSymbol("D3$Click").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("AccentColor").readResolve());
    static final FString Lit50 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit51 = ((SimpleSymbol) new SimpleSymbol("D4").readResolve());
    static final IntNum Lit52;
    static final IntNum Lit53 = IntNum.make(20);
    static final IntNum Lit54 = IntNum.make(1);
    static final IntNum Lit55;
    static final FString Lit56 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit57 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final SimpleSymbol Lit58 = ((SimpleSymbol) new SimpleSymbol("D5").readResolve());
    static final IntNum Lit59;
    static final IntNum Lit6;
    static final IntNum Lit60 = IntNum.make(15);
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("Hint").readResolve());
    static final FString Lit62 = new FString("com.google.appinventor.components.runtime.TextBox");
    static final FString Lit63 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit64 = ((SimpleSymbol) new SimpleSymbol("D6").readResolve());
    static final IntNum Lit65;
    static final IntNum Lit66 = IntNum.make(30);
    static final FString Lit67 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit68 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit69 = ((SimpleSymbol) new SimpleSymbol("D7").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("number").readResolve());
    static final IntNum Lit70;
    static final IntNum Lit71;
    static final FString Lit72 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit73 = new FString("com.google.appinventor.components.runtime.PasswordTextBox");
    static final SimpleSymbol Lit74 = ((SimpleSymbol) new SimpleSymbol("D8").readResolve());
    static final FString Lit75 = new FString("com.google.appinventor.components.runtime.PasswordTextBox");
    static final FString Lit76 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit77 = ((SimpleSymbol) new SimpleSymbol("D9").readResolve());
    static final IntNum Lit78;
    static final IntNum Lit79;
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("AppName").readResolve());
    static final FString Lit80 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit81 = new FString("com.google.appinventor.components.runtime.PasswordTextBox");
    static final SimpleSymbol Lit82 = ((SimpleSymbol) new SimpleSymbol("D10").readResolve());
    static final FString Lit83 = new FString("com.google.appinventor.components.runtime.PasswordTextBox");
    static final FString Lit84 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit85 = ((SimpleSymbol) new SimpleSymbol("D11").readResolve());
    static final IntNum Lit86;
    static final FString Lit87 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit88 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit89 = ((SimpleSymbol) new SimpleSymbol("D12").readResolve());
    static final SimpleSymbol Lit9;
    static final IntNum Lit90;
    static final IntNum Lit91;
    static final FString Lit92 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit93 = PairWithPosition.make(Lit9, PairWithPosition.make(Lit9, PairWithPosition.make(Lit9, PairWithPosition.make(Lit9, PairWithPosition.make(Lit9, PairWithPosition.make(Lit9, PairWithPosition.make(Lit9, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen4.yail", 758056), "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen4.yail", 758051), "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen4.yail", 758046), "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen4.yail", 758041), "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen4.yail", 758036), "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen4.yail", 758031), "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen4.yail", 758025);
    static final PairWithPosition Lit94;
    static final SimpleSymbol Lit95 = ((SimpleSymbol) new SimpleSymbol("Web1").readResolve());
    static final SimpleSymbol Lit96 = ((SimpleSymbol) new SimpleSymbol("Url").readResolve());
    static final SimpleSymbol Lit97 = ((SimpleSymbol) new SimpleSymbol("Get").readResolve());
    static final SimpleSymbol Lit98 = ((SimpleSymbol) new SimpleSymbol("D12$Click").readResolve());
    static final FString Lit99 = new FString("com.google.appinventor.components.runtime.TinyDB");
    public static Screen4 Screen4;
    static final ModuleMethod lambda$Fn1 = null;
    static final ModuleMethod lambda$Fn10 = null;
    static final ModuleMethod lambda$Fn11 = null;
    static final ModuleMethod lambda$Fn12 = null;
    static final ModuleMethod lambda$Fn13 = null;
    static final ModuleMethod lambda$Fn14 = null;
    static final ModuleMethod lambda$Fn15 = null;
    static final ModuleMethod lambda$Fn16 = null;
    static final ModuleMethod lambda$Fn17 = null;
    static final ModuleMethod lambda$Fn18 = null;
    static final ModuleMethod lambda$Fn19 = null;
    static final ModuleMethod lambda$Fn2 = null;
    static final ModuleMethod lambda$Fn20 = null;
    static final ModuleMethod lambda$Fn21 = null;
    static final ModuleMethod lambda$Fn22 = null;
    static final ModuleMethod lambda$Fn23 = null;
    static final ModuleMethod lambda$Fn24 = null;
    static final ModuleMethod lambda$Fn25 = null;
    static final ModuleMethod lambda$Fn26 = null;
    static final ModuleMethod lambda$Fn27 = null;
    static final ModuleMethod lambda$Fn28 = null;
    static final ModuleMethod lambda$Fn3 = null;
    static final ModuleMethod lambda$Fn4 = null;
    static final ModuleMethod lambda$Fn5 = null;
    static final ModuleMethod lambda$Fn6 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn9 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public HorizontalScrollArrangement D1;
    public PasswordTextBox D10;
    public HorizontalScrollArrangement D11;
    public Button D12;
    public final ModuleMethod D12$Click;
    public Button D2;
    public final ModuleMethod D2$Click;
    public Button D3;
    public final ModuleMethod D3$Click;
    public Label D4;
    public TextBox D5;
    public HorizontalScrollArrangement D6;
    public Label D7;
    public PasswordTextBox D8;
    public Label D9;
    public TinyDB TinyDB1;
    public Web Web1;
    public final ModuleMethod add$Mnto$Mncomponents;
    public final ModuleMethod add$Mnto$Mnevents;
    public final ModuleMethod add$Mnto$Mnform$Mndo$Mnafter$Mncreation;
    public final ModuleMethod add$Mnto$Mnform$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvar$Mnenvironment;
    public final ModuleMethod add$Mnto$Mnglobal$Mnvars;
    public final ModuleMethod android$Mnlog$Mnform;
    public LList components$Mnto$Mncreate;
    public final ModuleMethod dispatchEvent;
    public final ModuleMethod dispatchGenericEvent;
    public LList events$Mnto$Mnregister;
    public LList form$Mndo$Mnafter$Mncreation;
    public Environment form$Mnenvironment;
    public Symbol form$Mnname$Mnsymbol;
    public final ModuleMethod get$Mnsimple$Mnname;
    public Environment global$Mnvar$Mnenvironment;
    public LList global$Mnvars$Mnto$Mncreate;
    public final ModuleMethod is$Mnbound$Mnin$Mnform$Mnenvironment;
    public final ModuleMethod lookup$Mnhandler;
    public final ModuleMethod lookup$Mnin$Mnform$Mnenvironment;
    public final ModuleMethod onCreate;
    public final ModuleMethod process$Mnexception;
    public final ModuleMethod send$Mnerror;

    /* compiled from: Screen4.yail */
    public class frame extends ModuleBody {
        Screen4 $main = this;

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 1:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 2:
                    if (!(obj instanceof Screen4)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 3:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 5:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 7:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 12:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 13:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 14:
                    if (!(obj instanceof Screen4)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                default:
                    return super.match1(moduleMethod, obj, callContext);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 4:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 5:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 8:
                    if (!(obj instanceof Symbol)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 9:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 11:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                case 17:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.proc = moduleMethod;
                    callContext.pc = 2;
                    return 0;
                default:
                    return super.match2(moduleMethod, obj, obj2, callContext);
            }
        }

        public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 10:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.pc = 4;
                    return 0;
                case 15:
                    if (!(obj instanceof Screen4)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    if (!(obj2 instanceof Component)) {
                        return -786430;
                    }
                    callContext.value2 = obj2;
                    if (!(obj3 instanceof String)) {
                        return -786429;
                    }
                    callContext.value3 = obj3;
                    if (!(obj4 instanceof String)) {
                        return -786428;
                    }
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.pc = 4;
                    return 0;
                case 16:
                    if (!(obj instanceof Screen4)) {
                        return -786431;
                    }
                    callContext.value1 = obj;
                    if (!(obj2 instanceof Component)) {
                        return -786430;
                    }
                    callContext.value2 = obj2;
                    if (!(obj3 instanceof String)) {
                        return -786429;
                    }
                    callContext.value3 = obj3;
                    callContext.value4 = obj4;
                    callContext.proc = moduleMethod;
                    callContext.pc = 4;
                    return 0;
                default:
                    return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
            }
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 1:
                    return this.$main.getSimpleName(obj);
                case 2:
                    try {
                        this.$main.onCreate((Bundle) obj);
                        return Values.empty;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "onCreate", 1, obj);
                    }
                case 3:
                    this.$main.androidLogForm(obj);
                    return Values.empty;
                case 5:
                    try {
                        return this.$main.lookupInFormEnvironment((Symbol) obj);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "lookup-in-form-environment", 1, obj);
                    }
                case 7:
                    try {
                        return this.$main.isBoundInFormEnvironment((Symbol) obj) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "is-bound-in-form-environment", 1, obj);
                    }
                case 12:
                    this.$main.addToFormDoAfterCreation(obj);
                    return Values.empty;
                case 13:
                    this.$main.sendError(obj);
                    return Values.empty;
                case 14:
                    this.$main.processException(obj);
                    return Values.empty;
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
            boolean z = true;
            switch (moduleMethod.selector) {
                case 10:
                    this.$main.addToComponents(obj, obj2, obj3, obj4);
                    return Values.empty;
                case 15:
                    try {
                        try {
                            try {
                                try {
                                    return this.$main.dispatchEvent((Component) obj, (String) obj2, (String) obj3, (Object[]) obj4) ? Boolean.TRUE : Boolean.FALSE;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "dispatchEvent", 4, obj4);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "dispatchEvent", 3, obj3);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "dispatchEvent", 2, obj2);
                        }
                    } catch (ClassCastException e4) {
                        throw new WrongType(e4, "dispatchEvent", 1, obj);
                    }
                case 16:
                    Screen4 screen4 = this.$main;
                    try {
                        Component component = (Component) obj;
                        try {
                            String str = (String) obj2;
                            try {
                                if (obj3 == Boolean.FALSE) {
                                    z = false;
                                }
                                try {
                                    screen4.dispatchGenericEvent(component, str, z, (Object[]) obj4);
                                    return Values.empty;
                                } catch (ClassCastException e5) {
                                    throw new WrongType(e5, "dispatchGenericEvent", 4, obj4);
                                }
                            } catch (ClassCastException e6) {
                                throw new WrongType(e6, "dispatchGenericEvent", 3, obj3);
                            }
                        } catch (ClassCastException e7) {
                            throw new WrongType(e7, "dispatchGenericEvent", 2, obj2);
                        }
                    } catch (ClassCastException e8) {
                        throw new WrongType(e8, "dispatchGenericEvent", 1, obj);
                    }
                default:
                    return super.apply4(moduleMethod, obj, obj2, obj3, obj4);
            }
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            switch (moduleMethod.selector) {
                case 4:
                    try {
                        this.$main.addToFormEnvironment((Symbol) obj, obj2);
                        return Values.empty;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "add-to-form-environment", 1, obj);
                    }
                case 5:
                    try {
                        return this.$main.lookupInFormEnvironment((Symbol) obj, obj2);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "lookup-in-form-environment", 1, obj);
                    }
                case 8:
                    try {
                        this.$main.addToGlobalVarEnvironment((Symbol) obj, obj2);
                        return Values.empty;
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "add-to-global-var-environment", 1, obj);
                    }
                case 9:
                    this.$main.addToEvents(obj, obj2);
                    return Values.empty;
                case 11:
                    this.$main.addToGlobalVars(obj, obj2);
                    return Values.empty;
                case 17:
                    return this.$main.lookupHandler(obj, obj2);
                default:
                    return super.apply2(moduleMethod, obj, obj2);
            }
        }

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 18:
                    return Screen4.lambda2();
                case 19:
                    this.$main.$define();
                    return Values.empty;
                case 20:
                    return Screen4.lambda3();
                case 21:
                    return Screen4.lambda4();
                case 22:
                    return Screen4.lambda5();
                case 23:
                    return Screen4.lambda6();
                case 24:
                    return Screen4.lambda7();
                case 25:
                    return Screen4.lambda8();
                case 26:
                    return Screen4.lambda9();
                case 27:
                    return this.$main.D2$Click();
                case 28:
                    return Screen4.lambda10();
                case 29:
                    return Screen4.lambda11();
                case 30:
                    return this.$main.D3$Click();
                case 31:
                    return Screen4.lambda12();
                case 32:
                    return Screen4.lambda13();
                case 33:
                    return Screen4.lambda14();
                case 34:
                    return Screen4.lambda15();
                case 35:
                    return Screen4.lambda16();
                case 36:
                    return Screen4.lambda17();
                case 37:
                    return Screen4.lambda18();
                case 38:
                    return Screen4.lambda19();
                case 39:
                    return Screen4.lambda20();
                case 40:
                    return Screen4.lambda21();
                case 41:
                    return Screen4.lambda22();
                case 42:
                    return Screen4.lambda23();
                case 43:
                    return Screen4.lambda24();
                case 44:
                    return Screen4.lambda25();
                case 45:
                    return Screen4.lambda26();
                case 46:
                    return Screen4.lambda27();
                case 47:
                    return Screen4.lambda28();
                case 48:
                    return Screen4.lambda29();
                case 49:
                    return this.$main.D12$Click();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 18:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 19:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 20:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 21:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 22:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 23:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 24:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 25:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 26:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 27:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 28:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 29:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 30:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 31:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 32:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 33:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 34:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 35:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 36:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 37:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 38:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 39:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 40:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 41:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 42:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 43:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 44:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 45:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 46:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 47:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 48:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 49:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }
    }

    static {
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit9 = simpleSymbol;
        Lit94 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(Lit9, PairWithPosition.make(Lit9, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen4.yail", 758192), "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen4.yail", 758187), "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen4.yail", 758181);
        int[] iArr = new int[2];
        iArr[0] = -1;
        Lit91 = IntNum.make(iArr);
        int[] iArr2 = new int[2];
        iArr2[0] = -16777216;
        Lit90 = IntNum.make(iArr2);
        int[] iArr3 = new int[2];
        iArr3[0] = -16777216;
        Lit86 = IntNum.make(iArr3);
        int[] iArr4 = new int[2];
        iArr4[0] = -1;
        Lit79 = IntNum.make(iArr4);
        int[] iArr5 = new int[2];
        iArr5[0] = -16777216;
        Lit78 = IntNum.make(iArr5);
        int[] iArr6 = new int[2];
        iArr6[0] = -1;
        Lit71 = IntNum.make(iArr6);
        int[] iArr7 = new int[2];
        iArr7[0] = -16777216;
        Lit70 = IntNum.make(iArr7);
        int[] iArr8 = new int[2];
        iArr8[0] = -16777216;
        Lit65 = IntNum.make(iArr8);
        int[] iArr9 = new int[2];
        iArr9[0] = -1;
        Lit59 = IntNum.make(iArr9);
        int[] iArr10 = new int[2];
        iArr10[0] = -1;
        Lit55 = IntNum.make(iArr10);
        int[] iArr11 = new int[2];
        iArr11[0] = -16777216;
        Lit52 = IntNum.make(iArr11);
        int[] iArr12 = new int[2];
        iArr12[0] = -1;
        Lit46 = IntNum.make(iArr12);
        int[] iArr13 = new int[2];
        iArr13[0] = -16777216;
        Lit39 = IntNum.make(iArr13);
        int[] iArr14 = new int[2];
        iArr14[0] = -16777216;
        Lit24 = IntNum.make(iArr14);
        int[] iArr15 = new int[2];
        iArr15[0] = -16777216;
        Lit15 = IntNum.make(iArr15);
        int[] iArr16 = new int[2];
        iArr16[0] = -16777216;
        Lit13 = IntNum.make(iArr16);
        int[] iArr17 = new int[2];
        iArr17[0] = -16777216;
        Lit11 = IntNum.make(iArr17);
        int[] iArr18 = new int[2];
        iArr18[0] = -16777216;
        Lit6 = IntNum.make(iArr18);
    }

    public Screen4() {
        ModuleInfo.register(this);
        frame frame2 = new frame();
        this.get$Mnsimple$Mnname = new ModuleMethod(frame2, 1, Lit104, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.onCreate = new ModuleMethod(frame2, 2, "onCreate", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.android$Mnlog$Mnform = new ModuleMethod(frame2, 3, Lit105, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(frame2, 4, Lit106, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 5, Lit107, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 7, Lit108, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(frame2, 8, Lit109, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(frame2, 9, Lit110, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(frame2, 10, Lit111, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(frame2, 11, Lit112, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(frame2, 12, Lit113, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(frame2, 13, Lit114, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(frame2, 14, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(frame2, 15, Lit115, 16388);
        this.dispatchGenericEvent = new ModuleMethod(frame2, 16, Lit116, 16388);
        this.lookup$Mnhandler = new ModuleMethod(frame2, 17, Lit117, 8194);
        ModuleMethod moduleMethod = new ModuleMethod(frame2, 18, null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime9130887760156434227.scm:622");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(frame2, 19, "$define", 0);
        lambda$Fn2 = new ModuleMethod(frame2, 20, null, 0);
        lambda$Fn3 = new ModuleMethod(frame2, 21, null, 0);
        lambda$Fn4 = new ModuleMethod(frame2, 22, null, 0);
        lambda$Fn5 = new ModuleMethod(frame2, 23, null, 0);
        lambda$Fn6 = new ModuleMethod(frame2, 24, null, 0);
        lambda$Fn7 = new ModuleMethod(frame2, 25, null, 0);
        lambda$Fn8 = new ModuleMethod(frame2, 26, null, 0);
        this.D2$Click = new ModuleMethod(frame2, 27, Lit35, 0);
        lambda$Fn9 = new ModuleMethod(frame2, 28, null, 0);
        lambda$Fn10 = new ModuleMethod(frame2, 29, null, 0);
        this.D3$Click = new ModuleMethod(frame2, 30, Lit49, 0);
        lambda$Fn11 = new ModuleMethod(frame2, 31, null, 0);
        lambda$Fn12 = new ModuleMethod(frame2, 32, null, 0);
        lambda$Fn13 = new ModuleMethod(frame2, 33, null, 0);
        lambda$Fn14 = new ModuleMethod(frame2, 34, null, 0);
        lambda$Fn15 = new ModuleMethod(frame2, 35, null, 0);
        lambda$Fn16 = new ModuleMethod(frame2, 36, null, 0);
        lambda$Fn17 = new ModuleMethod(frame2, 37, null, 0);
        lambda$Fn18 = new ModuleMethod(frame2, 38, null, 0);
        lambda$Fn19 = new ModuleMethod(frame2, 39, null, 0);
        lambda$Fn20 = new ModuleMethod(frame2, 40, null, 0);
        lambda$Fn21 = new ModuleMethod(frame2, 41, null, 0);
        lambda$Fn22 = new ModuleMethod(frame2, 42, null, 0);
        lambda$Fn23 = new ModuleMethod(frame2, 43, null, 0);
        lambda$Fn24 = new ModuleMethod(frame2, 44, null, 0);
        lambda$Fn25 = new ModuleMethod(frame2, 45, null, 0);
        lambda$Fn26 = new ModuleMethod(frame2, 46, null, 0);
        lambda$Fn27 = new ModuleMethod(frame2, 47, null, 0);
        lambda$Fn28 = new ModuleMethod(frame2, 48, null, 0);
        this.D12$Click = new ModuleMethod(frame2, 49, Lit98, 0);
    }

    public Object lookupInFormEnvironment(Symbol symbol) {
        return lookupInFormEnvironment(symbol, Boolean.FALSE);
    }

    public void run() {
        CallContext instance = CallContext.getInstance();
        Consumer consumer = instance.consumer;
        instance.consumer = VoidConsumer.instance;
        try {
            run(instance);
            th = null;
        } catch (Throwable th) {
            th = th;
        }
        ModuleBody.runCleanup(instance, th, consumer);
    }

    public final void run(CallContext $ctx) {
        String obj;
        Consumer $result = $ctx.consumer;
        runtime.$instance.run();
        this.$Stdebug$Mnform$St = Boolean.FALSE;
        this.form$Mnenvironment = Environment.make(misc.symbol$To$String(Lit0));
        FString stringAppend = strings.stringAppend(misc.symbol$To$String(Lit0), "-global-vars");
        if (stringAppend == null) {
            obj = null;
        } else {
            obj = stringAppend.toString();
        }
        this.global$Mnvar$Mnenvironment = Environment.make(obj);
        Screen4 = null;
        this.form$Mnname$Mnsymbol = Lit0;
        this.events$Mnto$Mnregister = LList.Empty;
        this.components$Mnto$Mncreate = LList.Empty;
        this.global$Mnvars$Mnto$Mncreate = LList.Empty;
        this.form$Mndo$Mnafter$Mncreation = LList.Empty;
        runtime.$instance.run();
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit3, ""), $result);
        } else {
            addToGlobalVars(Lit3, lambda$Fn2);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit4, ""), $result);
        } else {
            addToGlobalVars(Lit4, lambda$Fn3);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit0, Lit5, Lit6, Lit7);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit8, "Pixal", Lit9);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit10, Lit11, Lit7);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit12, Lit13, Lit7);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit14, Lit15, Lit7);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit16, Boolean.TRUE, Lit17);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit18, Boolean.FALSE, Lit17);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit19, "Fixed", Lit9);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit20, "Screen4", Lit9);
            Values.writeValues(runtime.setAndCoerceProperty$Ex(Lit0, Lit21, Boolean.FALSE, Lit17), $result);
        } else {
            addToFormDoAfterCreation(new Promise(lambda$Fn4));
        }
        this.D1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit22, Lit23, lambda$Fn5), $result);
        } else {
            addToComponents(Lit0, Lit27, Lit23, lambda$Fn6);
        }
        this.D2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit23, Lit28, Lit29, lambda$Fn7), $result);
        } else {
            addToComponents(Lit23, Lit33, Lit29, lambda$Fn8);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit35, this.D2$Click);
        } else {
            addToFormEnvironment(Lit35, this.D2$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "D2", "Click");
        } else {
            addToEvents(Lit29, Lit36);
        }
        this.D3 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit23, Lit37, Lit38, lambda$Fn9), $result);
        } else {
            addToComponents(Lit23, Lit47, Lit38, lambda$Fn10);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit49, this.D3$Click);
        } else {
            addToFormEnvironment(Lit49, this.D3$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "D3", "Click");
        } else {
            addToEvents(Lit38, Lit36);
        }
        this.D4 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit50, Lit51, lambda$Fn11), $result);
        } else {
            addToComponents(Lit0, Lit56, Lit51, lambda$Fn12);
        }
        this.D5 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit57, Lit58, lambda$Fn13), $result);
        } else {
            addToComponents(Lit0, Lit62, Lit58, lambda$Fn14);
        }
        this.D6 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit63, Lit64, lambda$Fn15), $result);
        } else {
            addToComponents(Lit0, Lit67, Lit64, lambda$Fn16);
        }
        this.D7 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit68, Lit69, lambda$Fn17), $result);
        } else {
            addToComponents(Lit0, Lit72, Lit69, lambda$Fn18);
        }
        this.D8 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit73, Lit74, lambda$Fn19), $result);
        } else {
            addToComponents(Lit0, Lit75, Lit74, lambda$Fn20);
        }
        this.D9 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit76, Lit77, lambda$Fn21), $result);
        } else {
            addToComponents(Lit0, Lit80, Lit77, lambda$Fn22);
        }
        this.D10 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit81, Lit82, lambda$Fn23), $result);
        } else {
            addToComponents(Lit0, Lit83, Lit82, lambda$Fn24);
        }
        this.D11 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit84, Lit85, lambda$Fn25), $result);
        } else {
            addToComponents(Lit0, Lit87, Lit85, lambda$Fn26);
        }
        this.D12 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit88, Lit89, lambda$Fn27), $result);
        } else {
            addToComponents(Lit0, Lit92, Lit89, lambda$Fn28);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit98, this.D12$Click);
        } else {
            addToFormEnvironment(Lit98, this.D12$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "D12", "Click");
        } else {
            addToEvents(Lit89, Lit36);
        }
        this.TinyDB1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit99, Lit100, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit101, Lit100, Boolean.FALSE);
        }
        this.Web1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit102, Lit95, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit103, Lit95, Boolean.FALSE);
        }
        runtime.initRuntime();
    }

    static String lambda3() {
        return "";
    }

    static String lambda4() {
        return "";
    }

    static Object lambda5() {
        runtime.setAndCoerceProperty$Ex(Lit0, Lit5, Lit6, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit8, "Pixal", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit10, Lit11, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit12, Lit13, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit14, Lit15, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit16, Boolean.TRUE, Lit17);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit18, Boolean.FALSE, Lit17);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit19, "Fixed", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit20, "Screen4", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit0, Lit21, Boolean.FALSE, Lit17);
    }

    static Object lambda6() {
        runtime.setAndCoerceProperty$Ex(Lit23, Lit10, Lit24, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit23, Lit25, Lit26, Lit7);
    }

    static Object lambda7() {
        runtime.setAndCoerceProperty$Ex(Lit23, Lit10, Lit24, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit23, Lit25, Lit26, Lit7);
    }

    static Object lambda8() {
        runtime.setAndCoerceProperty$Ex(Lit29, Lit30, Lit31, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit29, Lit32, "56673328.png", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit29, Lit25, Lit31, Lit7);
    }

    static Object lambda9() {
        runtime.setAndCoerceProperty$Ex(Lit29, Lit30, Lit31, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit29, Lit32, "56673328.png", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit29, Lit25, Lit31, Lit7);
    }

    public Object D2$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen5"), Lit34, "open another screen");
    }

    static Object lambda10() {
        runtime.setAndCoerceProperty$Ex(Lit38, Lit10, Lit39, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit38, Lit40, Lit41, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit38, Lit42, "Retour", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit38, Lit43, Lit44, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit38, Lit45, Lit46, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit38, Lit25, Lit26, Lit7);
    }

    static Object lambda11() {
        runtime.setAndCoerceProperty$Ex(Lit38, Lit10, Lit39, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit38, Lit40, Lit41, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit38, Lit42, "Retour", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit38, Lit43, Lit44, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit38, Lit45, Lit46, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit38, Lit25, Lit26, Lit7);
    }

    public Object D3$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen3"), Lit48, "open another screen");
    }

    static Object lambda12() {
        runtime.setAndCoerceProperty$Ex(Lit51, Lit10, Lit52, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit51, Lit40, Lit53, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit51, Lit42, "Identifiant ou Email", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit51, Lit43, Lit54, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit51, Lit45, Lit55, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit51, Lit25, Lit26, Lit7);
    }

    static Object lambda13() {
        runtime.setAndCoerceProperty$Ex(Lit51, Lit10, Lit52, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit51, Lit40, Lit53, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit51, Lit42, "Identifiant ou Email", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit51, Lit43, Lit54, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit51, Lit45, Lit55, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit51, Lit25, Lit26, Lit7);
    }

    static Object lambda14() {
        runtime.setAndCoerceProperty$Ex(Lit58, Lit10, Lit59, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit58, Lit40, Lit60, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit58, Lit61, "Identifiant", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit58, Lit25, Lit26, Lit7);
    }

    static Object lambda15() {
        runtime.setAndCoerceProperty$Ex(Lit58, Lit10, Lit59, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit58, Lit40, Lit60, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit58, Lit61, "Identifiant", Lit9);
        return runtime.setAndCoerceProperty$Ex(Lit58, Lit25, Lit26, Lit7);
    }

    static Object lambda16() {
        runtime.setAndCoerceProperty$Ex(Lit64, Lit10, Lit65, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit64, Lit30, Lit66, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit64, Lit25, Lit26, Lit7);
    }

    static Object lambda17() {
        runtime.setAndCoerceProperty$Ex(Lit64, Lit10, Lit65, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit64, Lit30, Lit66, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit64, Lit25, Lit26, Lit7);
    }

    static Object lambda18() {
        runtime.setAndCoerceProperty$Ex(Lit69, Lit10, Lit70, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit69, Lit40, Lit53, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit69, Lit42, "Mot de passe", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit69, Lit43, Lit54, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit69, Lit45, Lit71, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit69, Lit25, Lit26, Lit7);
    }

    static Object lambda19() {
        runtime.setAndCoerceProperty$Ex(Lit69, Lit10, Lit70, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit69, Lit40, Lit53, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit69, Lit42, "Mot de passe", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit69, Lit43, Lit54, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit69, Lit45, Lit71, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit69, Lit25, Lit26, Lit7);
    }

    static Object lambda20() {
        runtime.setAndCoerceProperty$Ex(Lit74, Lit40, Lit60, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit74, Lit25, Lit26, Lit7);
    }

    static Object lambda21() {
        runtime.setAndCoerceProperty$Ex(Lit74, Lit40, Lit60, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit74, Lit25, Lit26, Lit7);
    }

    static Object lambda22() {
        runtime.setAndCoerceProperty$Ex(Lit77, Lit10, Lit78, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit77, Lit40, Lit53, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit77, Lit42, "Vérifier mot de passe", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit77, Lit43, Lit54, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit77, Lit45, Lit79, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit77, Lit25, Lit26, Lit7);
    }

    static Object lambda23() {
        runtime.setAndCoerceProperty$Ex(Lit77, Lit10, Lit78, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit77, Lit40, Lit53, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit77, Lit42, "Vérifier mot de passe", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit77, Lit43, Lit54, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit77, Lit45, Lit79, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit77, Lit25, Lit26, Lit7);
    }

    static Object lambda24() {
        runtime.setAndCoerceProperty$Ex(Lit82, Lit40, Lit60, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit82, Lit25, Lit26, Lit7);
    }

    static Object lambda25() {
        runtime.setAndCoerceProperty$Ex(Lit82, Lit40, Lit60, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit82, Lit25, Lit26, Lit7);
    }

    static Object lambda26() {
        runtime.setAndCoerceProperty$Ex(Lit85, Lit10, Lit86, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit85, Lit30, Lit66, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit85, Lit25, Lit26, Lit7);
    }

    static Object lambda27() {
        runtime.setAndCoerceProperty$Ex(Lit85, Lit10, Lit86, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit85, Lit30, Lit66, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit85, Lit25, Lit26, Lit7);
    }

    static Object lambda28() {
        runtime.setAndCoerceProperty$Ex(Lit89, Lit10, Lit90, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit89, Lit40, Lit53, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit89, Lit42, "S'inscrire", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit89, Lit45, Lit91, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit89, Lit25, Lit26, Lit7);
    }

    static Object lambda29() {
        runtime.setAndCoerceProperty$Ex(Lit89, Lit10, Lit90, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit89, Lit40, Lit53, Lit7);
        runtime.setAndCoerceProperty$Ex(Lit89, Lit42, "S'inscrire", Lit9);
        runtime.setAndCoerceProperty$Ex(Lit89, Lit45, Lit91, Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit89, Lit25, Lit26, Lit7);
    }

    public Object D12$Click() {
        runtime.setThisForm();
        SimpleSymbol simpleSymbol = Lit4;
        ModuleMethod moduleMethod = strings.string$Mnappend;
        Pair list1 = LList.list1("http://vrc.kellis.fr/android/actions/register.php?register=TRUE");
        LList.chain1(LList.chain1(LList.chain4(list1, "&username=", runtime.get$Mnproperty.apply2(Lit58, Lit42), "&password=", runtime.get$Mnproperty.apply2(Lit74, Lit42)), "&password2="), runtime.get$Mnproperty.apply2(Lit82, Lit42));
        runtime.addGlobalVarToCurrentFormEnvironment(simpleSymbol, runtime.callYailPrimitive(moduleMethod, list1, Lit93, "join"));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit3, runtime.callYailPrimitive(runtime.string$Mnreplace$Mnall, LList.list3(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St), "", "%20"), Lit94, "replace all"));
        runtime.setAndCoerceProperty$Ex(Lit95, Lit96, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St), Lit9);
        return runtime.callComponentMethod(Lit95, Lit97, LList.Empty, LList.Empty);
    }

    public String getSimpleName(Object object) {
        return object.getClass().getSimpleName();
    }

    public void onCreate(Bundle icicle) {
        AppInventorCompatActivity.setClassicModeFromYail(true);
        super.onCreate(icicle);
    }

    public void androidLogForm(Object message) {
    }

    public void addToFormEnvironment(Symbol name, Object object) {
        androidLogForm(Format.formatToString(0, "Adding ~A to env ~A with value ~A", name, this.form$Mnenvironment, object));
        this.form$Mnenvironment.put(name, object);
    }

    public Object lookupInFormEnvironment(Symbol name, Object default$Mnvalue) {
        boolean x = ((this.form$Mnenvironment == null ? 1 : 0) + 1) & true;
        if (x) {
            if (!this.form$Mnenvironment.isBound(name)) {
                return default$Mnvalue;
            }
        } else if (!x) {
            return default$Mnvalue;
        }
        return this.form$Mnenvironment.get(name);
    }

    public boolean isBoundInFormEnvironment(Symbol name) {
        return this.form$Mnenvironment.isBound(name);
    }

    public void addToGlobalVarEnvironment(Symbol name, Object object) {
        androidLogForm(Format.formatToString(0, "Adding ~A to env ~A with value ~A", name, this.global$Mnvar$Mnenvironment, object));
        this.global$Mnvar$Mnenvironment.put(name, object);
    }

    public void addToEvents(Object component$Mnname, Object event$Mnname) {
        this.events$Mnto$Mnregister = lists.cons(lists.cons(component$Mnname, event$Mnname), this.events$Mnto$Mnregister);
    }

    public void addToComponents(Object container$Mnname, Object component$Mntype, Object component$Mnname, Object init$Mnthunk) {
        this.components$Mnto$Mncreate = lists.cons(LList.list4(container$Mnname, component$Mntype, component$Mnname, init$Mnthunk), this.components$Mnto$Mncreate);
    }

    public void addToGlobalVars(Object var, Object val$Mnthunk) {
        this.global$Mnvars$Mnto$Mncreate = lists.cons(LList.list2(var, val$Mnthunk), this.global$Mnvars$Mnto$Mncreate);
    }

    public void addToFormDoAfterCreation(Object thunk) {
        this.form$Mndo$Mnafter$Mncreation = lists.cons(thunk, this.form$Mndo$Mnafter$Mncreation);
    }

    public void sendError(Object error) {
        RetValManager.sendError(error == null ? null : error.toString());
    }

    public void processException(Object ex) {
        Object apply1 = Scheme.applyToArgs.apply1(GetNamedPart.getNamedPart.apply2(ex, Lit1));
        RuntimeErrorAlert.alert(this, apply1 == null ? null : apply1.toString(), ex instanceof YailRuntimeError ? ((YailRuntimeError) ex).getErrorType() : "Runtime Error", "End Application");
    }

    public boolean dispatchEvent(Component componentObject, String registeredComponentName, String eventName, Object[] args) {
        boolean x;
        SimpleSymbol registeredObject = misc.string$To$Symbol(registeredComponentName);
        if (!isBoundInFormEnvironment(registeredObject)) {
            EventDispatcher.unregisterEventForDelegation(this, registeredComponentName, eventName);
            return false;
        } else if (lookupInFormEnvironment(registeredObject) != componentObject) {
            return false;
        } else {
            try {
                Scheme.apply.apply2(lookupHandler(registeredComponentName, eventName), LList.makeList(args, 0));
                return true;
            } catch (PermissionException exception) {
                exception.printStackTrace();
                if (this == componentObject) {
                    x = true;
                } else {
                    x = false;
                }
                if (!x ? x : IsEqual.apply(eventName, "PermissionNeeded")) {
                    processException(exception);
                } else {
                    PermissionDenied(componentObject, eventName, exception.getPermissionNeeded());
                }
                return false;
            } catch (Throwable exception2) {
                androidLogForm(exception2.getMessage());
                exception2.printStackTrace();
                processException(exception2);
                return false;
            }
        }
    }

    public void dispatchGenericEvent(Component componentObject, String eventName, boolean notAlreadyHandled, Object[] args) {
        Boolean bool;
        boolean x = true;
        Object handler = lookupInFormEnvironment(misc.string$To$Symbol(strings.stringAppend("any$", getSimpleName(componentObject), "$", eventName)));
        if (handler != Boolean.FALSE) {
            try {
                Apply apply = Scheme.apply;
                if (notAlreadyHandled) {
                    bool = Boolean.TRUE;
                } else {
                    bool = Boolean.FALSE;
                }
                apply.apply2(handler, lists.cons(componentObject, lists.cons(bool, LList.makeList(args, 0))));
            } catch (PermissionException exception) {
                exception.printStackTrace();
                if (this != componentObject) {
                    x = false;
                }
                if (!x ? x : IsEqual.apply(eventName, "PermissionNeeded")) {
                    processException(exception);
                } else {
                    PermissionDenied(componentObject, eventName, exception.getPermissionNeeded());
                }
            } catch (Throwable exception2) {
                androidLogForm(exception2.getMessage());
                exception2.printStackTrace();
                processException(exception2);
            }
        }
    }

    public Object lookupHandler(Object componentName, Object eventName) {
        String str = null;
        String obj = componentName == null ? null : componentName.toString();
        if (eventName != null) {
            str = eventName.toString();
        }
        return lookupInFormEnvironment(misc.string$To$Symbol(EventDispatcher.makeFullEventName(obj, str)));
    }

    public void $define() {
        Object reverse;
        Object obj;
        Object reverse2;
        Object obj2;
        Object obj3;
        Object var;
        Object component$Mnname;
        Object obj4;
        Language.setDefaults(Scheme.getInstance());
        try {
            run();
        } catch (Exception exception) {
            androidLogForm(exception.getMessage());
            processException(exception);
        }
        Screen4 = this;
        addToFormEnvironment(Lit0, this);
        Object obj5 = this.events$Mnto$Mnregister;
        while (obj5 != LList.Empty) {
            try {
                Pair arg0 = (Pair) obj5;
                Object event$Mninfo = arg0.getCar();
                Object apply1 = lists.car.apply1(event$Mninfo);
                String obj6 = apply1 == null ? null : apply1.toString();
                Object apply12 = lists.cdr.apply1(event$Mninfo);
                EventDispatcher.registerEventForDelegation(this, obj6, apply12 == null ? null : apply12.toString());
                obj5 = arg0.getCdr();
            } catch (ClassCastException e) {
                WrongType wrongType = new WrongType(e, "arg0", -2, obj5);
                throw wrongType;
            }
        }
        try {
            LList components = lists.reverse(this.components$Mnto$Mncreate);
            addToGlobalVars(Lit2, lambda$Fn1);
            reverse = lists.reverse(this.form$Mndo$Mnafter$Mncreation);
            while (reverse != LList.Empty) {
                Pair arg02 = (Pair) reverse;
                misc.force(arg02.getCar());
                reverse = arg02.getCdr();
            }
            obj = components;
            while (obj != LList.Empty) {
                Pair arg03 = (Pair) obj;
                Object component$Mninfo = arg03.getCar();
                component$Mnname = lists.caddr.apply1(component$Mninfo);
                lists.cadddr.apply1(component$Mninfo);
                Object component$Mnobject = Invoke.make.apply2(lists.cadr.apply1(component$Mninfo), lookupInFormEnvironment((Symbol) lists.car.apply1(component$Mninfo)));
                SlotSet.set$Mnfield$Ex.apply3(this, component$Mnname, component$Mnobject);
                addToFormEnvironment((Symbol) component$Mnname, component$Mnobject);
                obj = arg03.getCdr();
            }
            reverse2 = lists.reverse(this.global$Mnvars$Mnto$Mncreate);
            while (reverse2 != LList.Empty) {
                Pair arg04 = (Pair) reverse2;
                Object var$Mnval = arg04.getCar();
                var = lists.car.apply1(var$Mnval);
                addToGlobalVarEnvironment((Symbol) var, Scheme.applyToArgs.apply1(lists.cadr.apply1(var$Mnval)));
                reverse2 = arg04.getCdr();
            }
            Object obj7 = components;
            obj2 = obj7;
            while (obj2 != LList.Empty) {
                Pair arg05 = (Pair) obj2;
                Object component$Mninfo2 = arg05.getCar();
                lists.caddr.apply1(component$Mninfo2);
                Object init$Mnthunk = lists.cadddr.apply1(component$Mninfo2);
                if (init$Mnthunk != Boolean.FALSE) {
                    Scheme.applyToArgs.apply1(init$Mnthunk);
                }
                obj2 = arg05.getCdr();
            }
            obj3 = obj7;
            while (obj3 != LList.Empty) {
                Pair arg06 = (Pair) obj3;
                Object component$Mninfo3 = arg06.getCar();
                Object component$Mnname2 = lists.caddr.apply1(component$Mninfo3);
                lists.cadddr.apply1(component$Mninfo3);
                callInitialize(SlotGet.field.apply2(this, component$Mnname2));
                obj3 = arg06.getCdr();
            }
        } catch (ClassCastException e2) {
            WrongType wrongType2 = new WrongType(e2, "arg0", -2, obj3);
            throw wrongType2;
        } catch (ClassCastException e3) {
            WrongType wrongType3 = new WrongType(e3, "arg0", -2, obj2);
            throw wrongType3;
        } catch (ClassCastException e4) {
            WrongType wrongType4 = new WrongType(e4, "add-to-global-var-environment", 0, var);
            throw wrongType4;
        } catch (ClassCastException e5) {
            WrongType wrongType5 = new WrongType(e5, "arg0", -2, reverse2);
            throw wrongType5;
        } catch (ClassCastException e6) {
            WrongType wrongType6 = new WrongType(e6, "add-to-form-environment", 0, component$Mnname);
            throw wrongType6;
        } catch (ClassCastException e7) {
            WrongType wrongType7 = new WrongType(e7, "lookup-in-form-environment", 0, obj4);
            throw wrongType7;
        } catch (ClassCastException e8) {
            WrongType wrongType8 = new WrongType(e8, "arg0", -2, obj);
            throw wrongType8;
        } catch (ClassCastException e9) {
            WrongType wrongType9 = new WrongType(e9, "arg0", -2, reverse);
            throw wrongType9;
        } catch (YailRuntimeError exception2) {
            processException(exception2);
        }
    }

    public static SimpleSymbol lambda1symbolAppend$V(Object[] argsArray) {
        LList symbols = LList.makeList(argsArray, 0);
        Apply apply = Scheme.apply;
        ModuleMethod moduleMethod = strings.string$Mnappend;
        Object obj = LList.Empty;
        Object obj2 = symbols;
        while (obj2 != LList.Empty) {
            try {
                Pair arg0 = (Pair) obj2;
                Object arg02 = arg0.getCdr();
                Object car = arg0.getCar();
                try {
                    obj = Pair.make(misc.symbol$To$String((Symbol) car), obj);
                    obj2 = arg02;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "symbol->string", 1, car);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "arg0", -2, obj2);
            }
        }
        Object apply2 = apply.apply2(moduleMethod, LList.reverseInPlace(obj));
        try {
            return misc.string$To$Symbol((CharSequence) apply2);
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "string->symbol", 1, apply2);
        }
    }

    static Object lambda2() {
        return null;
    }
}
