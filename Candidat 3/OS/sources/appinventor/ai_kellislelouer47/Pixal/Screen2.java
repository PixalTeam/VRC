package appinventor.ai_kellislelouer47.Pixal;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AppInventorCompatActivity;
import com.google.appinventor.components.runtime.BluetoothClient;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalScrollArrangement;
import com.google.appinventor.components.runtime.ListPicker;
import com.google.appinventor.components.runtime.TinyDB;
import com.google.appinventor.components.runtime.VerticalArrangement;
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

/* compiled from: Screen2.yail */
public class Screen2 extends Form implements Runnable {
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("Screen2").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("getMessage").readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("PrimaryColor").readResolve());
    static final SimpleSymbol Lit100 = ((SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve());
    static final SimpleSymbol Lit101 = ((SimpleSymbol) new SimpleSymbol("send-error").readResolve());
    static final SimpleSymbol Lit102 = ((SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve());
    static final SimpleSymbol Lit103 = ((SimpleSymbol) new SimpleSymbol("dispatchGenericEvent").readResolve());
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve());
    static final SimpleSymbol Lit105 = ((SimpleSymbol) new SimpleSymbol("any").readResolve());
    static final IntNum Lit11;
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("PrimaryColorDark").readResolve());
    static final IntNum Lit13;
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("ShowListsAsJson").readResolve());
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve());
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("ShowStatusBar").readResolve());
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("Sizing").readResolve());
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("Title").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("TitleVisible").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve());
    static final FString Lit20 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("B1").readResolve());
    static final IntNum Lit22;
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("Width").readResolve());
    static final IntNum Lit24 = IntNum.make(-2);
    static final FString Lit25 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit26 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("B2").readResolve());
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("Height").readResolve());
    static final IntNum Lit29 = IntNum.make(50);
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("AccentColor").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("Image").readResolve());
    static final FString Lit31 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit32 = PairWithPosition.make(Lit7, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen2.yail", 176206);
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol("B2$Click").readResolve());
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("Click").readResolve());
    static final FString Lit35 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit36 = ((SimpleSymbol) new SimpleSymbol("B3").readResolve());
    static final IntNum Lit37;
    static final SimpleSymbol Lit38 = ((SimpleSymbol) new SimpleSymbol("FontBold").readResolve());
    static final SimpleSymbol Lit39 = ((SimpleSymbol) new SimpleSymbol("FontSize").readResolve());
    static final IntNum Lit4;
    static final IntNum Lit40 = IntNum.make(25);
    static final SimpleSymbol Lit41 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("TextAlignment").readResolve());
    static final IntNum Lit43 = IntNum.make(2);
    static final SimpleSymbol Lit44 = ((SimpleSymbol) new SimpleSymbol("TextColor").readResolve());
    static final IntNum Lit45;
    static final FString Lit46 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit47 = PairWithPosition.make(Lit7, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen2.yail", 262222);
    static final SimpleSymbol Lit48 = ((SimpleSymbol) new SimpleSymbol("B3$Click").readResolve());
    static final FString Lit49 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("number").readResolve());
    static final SimpleSymbol Lit50 = ((SimpleSymbol) new SimpleSymbol("B4").readResolve());
    static final IntNum Lit51;
    static final IntNum Lit52 = IntNum.make(100);
    static final FString Lit53 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit54 = new FString("com.google.appinventor.components.runtime.ListPicker");
    static final SimpleSymbol Lit55 = ((SimpleSymbol) new SimpleSymbol("B5").readResolve());
    static final IntNum Lit56;
    static final IntNum Lit57 = IntNum.make(20);
    static final IntNum Lit58;
    static final FString Lit59 = new FString("com.google.appinventor.components.runtime.ListPicker");
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("AppName").readResolve());
    static final SimpleSymbol Lit60 = ((SimpleSymbol) new SimpleSymbol("Elements").readResolve());
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("BluetoothClient1").readResolve());
    static final SimpleSymbol Lit62 = ((SimpleSymbol) new SimpleSymbol("AddressesAndNames").readResolve());
    static final SimpleSymbol Lit63 = ((SimpleSymbol) new SimpleSymbol("list").readResolve());
    static final SimpleSymbol Lit64 = ((SimpleSymbol) new SimpleSymbol("B5$BeforePicking").readResolve());
    static final SimpleSymbol Lit65 = ((SimpleSymbol) new SimpleSymbol("BeforePicking").readResolve());
    static final SimpleSymbol Lit66 = ((SimpleSymbol) new SimpleSymbol("Connect").readResolve());
    static final SimpleSymbol Lit67 = ((SimpleSymbol) new SimpleSymbol("Selection").readResolve());
    static final PairWithPosition Lit68 = PairWithPosition.make(Lit7, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen2.yail", 393327);
    static final SimpleSymbol Lit69 = ((SimpleSymbol) new SimpleSymbol("SendText").readResolve());
    static final SimpleSymbol Lit7;
    static final PairWithPosition Lit70 = PairWithPosition.make(Lit7, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen2.yail", 393447);
    static final SimpleSymbol Lit71 = ((SimpleSymbol) new SimpleSymbol(TinyDB.DEFAULT_NAMESPACE).readResolve());
    static final SimpleSymbol Lit72 = ((SimpleSymbol) new SimpleSymbol("StoreValue").readResolve());
    static final PairWithPosition Lit73 = PairWithPosition.make(Lit7, PairWithPosition.make(Lit105, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen2.yail", 393573), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen2.yail", 393567);
    static final IntNum Lit74 = IntNum.make(1);
    static final PairWithPosition Lit75 = PairWithPosition.make(Lit7, PairWithPosition.make(Lit105, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen2.yail", 393667), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen2.yail", 393661);
    static final PairWithPosition Lit76 = PairWithPosition.make(Lit7, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen2.yail", 393779);
    static final SimpleSymbol Lit77 = ((SimpleSymbol) new SimpleSymbol("B5$AfterPicking").readResolve());
    static final SimpleSymbol Lit78 = ((SimpleSymbol) new SimpleSymbol("AfterPicking").readResolve());
    static final FString Lit79 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve());
    static final SimpleSymbol Lit80 = ((SimpleSymbol) new SimpleSymbol("B6").readResolve());
    static final IntNum Lit81;
    static final IntNum Lit82;
    static final FString Lit83 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit84 = ((SimpleSymbol) new SimpleSymbol("Disconnect").readResolve());
    static final PairWithPosition Lit85;
    static final SimpleSymbol Lit86 = ((SimpleSymbol) new SimpleSymbol("B6$Click").readResolve());
    static final FString Lit87 = new FString("com.google.appinventor.components.runtime.BluetoothClient");
    static final FString Lit88 = new FString("com.google.appinventor.components.runtime.BluetoothClient");
    static final FString Lit89 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final IntNum Lit9;
    static final FString Lit90 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final SimpleSymbol Lit91 = ((SimpleSymbol) new SimpleSymbol("get-simple-name").readResolve());
    static final SimpleSymbol Lit92 = ((SimpleSymbol) new SimpleSymbol("android-log-form").readResolve());
    static final SimpleSymbol Lit93 = ((SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve());
    static final SimpleSymbol Lit94 = ((SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve());
    static final SimpleSymbol Lit95 = ((SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve());
    static final SimpleSymbol Lit96 = ((SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve());
    static final SimpleSymbol Lit97 = ((SimpleSymbol) new SimpleSymbol("add-to-events").readResolve());
    static final SimpleSymbol Lit98 = ((SimpleSymbol) new SimpleSymbol("add-to-components").readResolve());
    static final SimpleSymbol Lit99 = ((SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve());
    public static Screen2 Screen2;
    static final ModuleMethod lambda$Fn1 = null;
    static final ModuleMethod lambda$Fn10 = null;
    static final ModuleMethod lambda$Fn11 = null;
    static final ModuleMethod lambda$Fn12 = null;
    static final ModuleMethod lambda$Fn13 = null;
    static final ModuleMethod lambda$Fn14 = null;
    static final ModuleMethod lambda$Fn2 = null;
    static final ModuleMethod lambda$Fn3 = null;
    static final ModuleMethod lambda$Fn4 = null;
    static final ModuleMethod lambda$Fn5 = null;
    static final ModuleMethod lambda$Fn6 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn9 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public HorizontalScrollArrangement B1;
    public Button B2;
    public final ModuleMethod B2$Click;
    public Button B3;
    public final ModuleMethod B3$Click;
    public VerticalArrangement B4;
    public ListPicker B5;
    public final ModuleMethod B5$AfterPicking;
    public final ModuleMethod B5$BeforePicking;
    public Button B6;
    public final ModuleMethod B6$Click;
    public BluetoothClient BluetoothClient1;
    public TinyDB TinyDB1;
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

    /* compiled from: Screen2.yail */
    public class frame extends ModuleBody {
        Screen2 $main = this;

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 1:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 2:
                    if (!(obj instanceof Screen2)) {
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
                    if (!(obj instanceof Screen2)) {
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
                    if (!(obj instanceof Screen2)) {
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
                    if (!(obj instanceof Screen2)) {
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
                    Screen2 screen2 = this.$main;
                    try {
                        Component component = (Component) obj;
                        try {
                            String str = (String) obj2;
                            try {
                                if (obj3 == Boolean.FALSE) {
                                    z = false;
                                }
                                try {
                                    screen2.dispatchGenericEvent(component, str, z, (Object[]) obj4);
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
                    return Screen2.lambda2();
                case 19:
                    this.$main.$define();
                    return Values.empty;
                case 20:
                    return Screen2.lambda3();
                case 21:
                    return Screen2.lambda4();
                case 22:
                    return Screen2.lambda5();
                case 23:
                    return Screen2.lambda6();
                case 24:
                    return Screen2.lambda7();
                case 25:
                    return this.$main.B2$Click();
                case 26:
                    return Screen2.lambda8();
                case 27:
                    return Screen2.lambda9();
                case 28:
                    return this.$main.B3$Click();
                case 29:
                    return Screen2.lambda10();
                case 30:
                    return Screen2.lambda11();
                case 31:
                    return Screen2.lambda12();
                case 32:
                    return Screen2.lambda13();
                case 33:
                    return this.$main.B5$BeforePicking();
                case 34:
                    return this.$main.B5$AfterPicking();
                case 35:
                    return Screen2.lambda14();
                case 36:
                    return Screen2.lambda15();
                case 37:
                    return this.$main.B6$Click();
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
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }
    }

    static {
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit7 = simpleSymbol;
        Lit85 = PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen2.yail", 471196);
        int[] iArr = new int[2];
        iArr[0] = -1;
        Lit82 = IntNum.make(iArr);
        int[] iArr2 = new int[2];
        iArr2[0] = -16777216;
        Lit81 = IntNum.make(iArr2);
        int[] iArr3 = new int[2];
        iArr3[0] = -1;
        Lit58 = IntNum.make(iArr3);
        int[] iArr4 = new int[2];
        iArr4[0] = -16777216;
        Lit56 = IntNum.make(iArr4);
        int[] iArr5 = new int[2];
        iArr5[0] = -16777216;
        Lit51 = IntNum.make(iArr5);
        int[] iArr6 = new int[2];
        iArr6[0] = -1;
        Lit45 = IntNum.make(iArr6);
        int[] iArr7 = new int[2];
        iArr7[0] = -16777216;
        Lit37 = IntNum.make(iArr7);
        int[] iArr8 = new int[2];
        iArr8[0] = -16777216;
        Lit22 = IntNum.make(iArr8);
        int[] iArr9 = new int[2];
        iArr9[0] = -16777216;
        Lit13 = IntNum.make(iArr9);
        int[] iArr10 = new int[2];
        iArr10[0] = -16777216;
        Lit11 = IntNum.make(iArr10);
        int[] iArr11 = new int[2];
        iArr11[0] = -16777216;
        Lit9 = IntNum.make(iArr11);
        int[] iArr12 = new int[2];
        iArr12[0] = -16777216;
        Lit4 = IntNum.make(iArr12);
    }

    public Screen2() {
        ModuleInfo.register(this);
        frame frame2 = new frame();
        this.get$Mnsimple$Mnname = new ModuleMethod(frame2, 1, Lit91, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.onCreate = new ModuleMethod(frame2, 2, "onCreate", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.android$Mnlog$Mnform = new ModuleMethod(frame2, 3, Lit92, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(frame2, 4, Lit93, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 5, Lit94, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 7, Lit95, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(frame2, 8, Lit96, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(frame2, 9, Lit97, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(frame2, 10, Lit98, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(frame2, 11, Lit99, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(frame2, 12, Lit100, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(frame2, 13, Lit101, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(frame2, 14, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(frame2, 15, Lit102, 16388);
        this.dispatchGenericEvent = new ModuleMethod(frame2, 16, Lit103, 16388);
        this.lookup$Mnhandler = new ModuleMethod(frame2, 17, Lit104, 8194);
        ModuleMethod moduleMethod = new ModuleMethod(frame2, 18, null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime7968961605135708732.scm:622");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(frame2, 19, "$define", 0);
        lambda$Fn2 = new ModuleMethod(frame2, 20, null, 0);
        lambda$Fn3 = new ModuleMethod(frame2, 21, null, 0);
        lambda$Fn4 = new ModuleMethod(frame2, 22, null, 0);
        lambda$Fn5 = new ModuleMethod(frame2, 23, null, 0);
        lambda$Fn6 = new ModuleMethod(frame2, 24, null, 0);
        this.B2$Click = new ModuleMethod(frame2, 25, Lit33, 0);
        lambda$Fn7 = new ModuleMethod(frame2, 26, null, 0);
        lambda$Fn8 = new ModuleMethod(frame2, 27, null, 0);
        this.B3$Click = new ModuleMethod(frame2, 28, Lit48, 0);
        lambda$Fn9 = new ModuleMethod(frame2, 29, null, 0);
        lambda$Fn10 = new ModuleMethod(frame2, 30, null, 0);
        lambda$Fn11 = new ModuleMethod(frame2, 31, null, 0);
        lambda$Fn12 = new ModuleMethod(frame2, 32, null, 0);
        this.B5$BeforePicking = new ModuleMethod(frame2, 33, Lit64, 0);
        this.B5$AfterPicking = new ModuleMethod(frame2, 34, Lit77, 0);
        lambda$Fn13 = new ModuleMethod(frame2, 35, null, 0);
        lambda$Fn14 = new ModuleMethod(frame2, 36, null, 0);
        this.B6$Click = new ModuleMethod(frame2, 37, Lit86, 0);
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
        Screen2 = null;
        this.form$Mnname$Mnsymbol = Lit0;
        this.events$Mnto$Mnregister = LList.Empty;
        this.components$Mnto$Mncreate = LList.Empty;
        this.global$Mnvars$Mnto$Mncreate = LList.Empty;
        this.form$Mndo$Mnafter$Mncreation = LList.Empty;
        runtime.$instance.run();
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit0, Lit3, Lit4, Lit5);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit6, "Pixal", Lit7);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit8, Lit9, Lit5);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit10, Lit11, Lit5);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit12, Lit13, Lit5);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit14, Boolean.FALSE, Lit15);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit16, Boolean.FALSE, Lit15);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit17, "Fixed", Lit7);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit18, "Screen2", Lit7);
            Values.writeValues(runtime.setAndCoerceProperty$Ex(Lit0, Lit19, Boolean.FALSE, Lit15), $result);
        } else {
            addToFormDoAfterCreation(new Promise(lambda$Fn2));
        }
        this.B1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit20, Lit21, lambda$Fn3), $result);
        } else {
            addToComponents(Lit0, Lit25, Lit21, lambda$Fn4);
        }
        this.B2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit21, Lit26, Lit27, lambda$Fn5), $result);
        } else {
            addToComponents(Lit21, Lit31, Lit27, lambda$Fn6);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit33, this.B2$Click);
        } else {
            addToFormEnvironment(Lit33, this.B2$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "B2", "Click");
        } else {
            addToEvents(Lit27, Lit34);
        }
        this.B3 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit21, Lit35, Lit36, lambda$Fn7), $result);
        } else {
            addToComponents(Lit21, Lit46, Lit36, lambda$Fn8);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit48, this.B3$Click);
        } else {
            addToFormEnvironment(Lit48, this.B3$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "B3", "Click");
        } else {
            addToEvents(Lit36, Lit34);
        }
        this.B4 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit49, Lit50, lambda$Fn9), $result);
        } else {
            addToComponents(Lit0, Lit53, Lit50, lambda$Fn10);
        }
        this.B5 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit50, Lit54, Lit55, lambda$Fn11), $result);
        } else {
            addToComponents(Lit50, Lit59, Lit55, lambda$Fn12);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit64, this.B5$BeforePicking);
        } else {
            addToFormEnvironment(Lit64, this.B5$BeforePicking);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "B5", "BeforePicking");
        } else {
            addToEvents(Lit55, Lit65);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit77, this.B5$AfterPicking);
        } else {
            addToFormEnvironment(Lit77, this.B5$AfterPicking);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "B5", "AfterPicking");
        } else {
            addToEvents(Lit55, Lit78);
        }
        this.B6 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit50, Lit79, Lit80, lambda$Fn13), $result);
        } else {
            addToComponents(Lit50, Lit83, Lit80, lambda$Fn14);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit86, this.B6$Click);
        } else {
            addToFormEnvironment(Lit86, this.B6$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "B6", "Click");
        } else {
            addToEvents(Lit80, Lit34);
        }
        this.BluetoothClient1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit87, Lit61, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit88, Lit61, Boolean.FALSE);
        }
        this.TinyDB1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit89, Lit71, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit90, Lit71, Boolean.FALSE);
        }
        runtime.initRuntime();
    }

    static Object lambda3() {
        runtime.setAndCoerceProperty$Ex(Lit0, Lit3, Lit4, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit6, "Pixal", Lit7);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit8, Lit9, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit10, Lit11, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit12, Lit13, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit14, Boolean.FALSE, Lit15);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit16, Boolean.FALSE, Lit15);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit17, "Fixed", Lit7);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit18, "Screen2", Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit0, Lit19, Boolean.FALSE, Lit15);
    }

    static Object lambda4() {
        runtime.setAndCoerceProperty$Ex(Lit21, Lit8, Lit22, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit21, Lit23, Lit24, Lit5);
    }

    static Object lambda5() {
        runtime.setAndCoerceProperty$Ex(Lit21, Lit8, Lit22, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit21, Lit23, Lit24, Lit5);
    }

    static Object lambda6() {
        runtime.setAndCoerceProperty$Ex(Lit27, Lit28, Lit29, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit27, Lit30, "56673328.png", Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit27, Lit23, Lit29, Lit5);
    }

    static Object lambda7() {
        runtime.setAndCoerceProperty$Ex(Lit27, Lit28, Lit29, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit27, Lit30, "56673328.png", Lit7);
        return runtime.setAndCoerceProperty$Ex(Lit27, Lit23, Lit29, Lit5);
    }

    public Object B2$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen1"), Lit32, "open another screen");
    }

    static Object lambda8() {
        runtime.setAndCoerceProperty$Ex(Lit36, Lit8, Lit37, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit36, Lit38, Boolean.TRUE, Lit15);
        runtime.setAndCoerceProperty$Ex(Lit36, Lit39, Lit40, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit36, Lit41, "Retour", Lit7);
        runtime.setAndCoerceProperty$Ex(Lit36, Lit42, Lit43, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit36, Lit44, Lit45, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit36, Lit23, Lit24, Lit5);
    }

    static Object lambda9() {
        runtime.setAndCoerceProperty$Ex(Lit36, Lit8, Lit37, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit36, Lit38, Boolean.TRUE, Lit15);
        runtime.setAndCoerceProperty$Ex(Lit36, Lit39, Lit40, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit36, Lit41, "Retour", Lit7);
        runtime.setAndCoerceProperty$Ex(Lit36, Lit42, Lit43, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit36, Lit44, Lit45, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit36, Lit23, Lit24, Lit5);
    }

    public Object B3$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen1"), Lit47, "open another screen");
    }

    static Object lambda10() {
        runtime.setAndCoerceProperty$Ex(Lit50, Lit8, Lit51, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit50, Lit28, Lit52, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit50, Lit23, Lit24, Lit5);
    }

    static Object lambda11() {
        runtime.setAndCoerceProperty$Ex(Lit50, Lit8, Lit51, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit50, Lit28, Lit52, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit50, Lit23, Lit24, Lit5);
    }

    static Object lambda12() {
        runtime.setAndCoerceProperty$Ex(Lit55, Lit8, Lit56, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit55, Lit39, Lit57, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit55, Lit28, Lit24, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit55, Lit41, "Liste des connexions", Lit7);
        runtime.setAndCoerceProperty$Ex(Lit55, Lit44, Lit58, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit55, Lit23, Lit24, Lit5);
    }

    static Object lambda13() {
        runtime.setAndCoerceProperty$Ex(Lit55, Lit8, Lit56, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit55, Lit39, Lit57, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit55, Lit28, Lit24, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit55, Lit41, "Liste des connexions", Lit7);
        runtime.setAndCoerceProperty$Ex(Lit55, Lit44, Lit58, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit55, Lit23, Lit24, Lit5);
    }

    public Object B5$BeforePicking() {
        runtime.setThisForm();
        return runtime.setAndCoerceProperty$Ex(Lit55, Lit60, runtime.get$Mnproperty.apply2(Lit61, Lit62), Lit63);
    }

    public Object B5$AfterPicking() {
        runtime.setThisForm();
        if (runtime.callComponentMethod(Lit61, Lit66, LList.list1(runtime.get$Mnproperty.apply2(Lit55, Lit67)), Lit68) == Boolean.FALSE) {
            return runtime.callComponentMethod(Lit61, Lit69, LList.list1("Déconnecté"), Lit76);
        }
        runtime.callComponentMethod(Lit61, Lit69, LList.list1(runtime.get$Mnproperty.apply2(Lit55, Lit67)), Lit70);
        runtime.callComponentMethod(Lit71, Lit72, LList.list2("bluetoothData", runtime.get$Mnproperty.apply2(Lit55, Lit67)), Lit73);
        return runtime.callComponentMethod(Lit71, Lit72, LList.list2("isBluetooth", Lit74), Lit75);
    }

    static Object lambda14() {
        runtime.setAndCoerceProperty$Ex(Lit80, Lit8, Lit81, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit39, Lit57, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit28, Lit24, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit41, "Déconnexion", Lit7);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit44, Lit82, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit80, Lit23, Lit24, Lit5);
    }

    static Object lambda15() {
        runtime.setAndCoerceProperty$Ex(Lit80, Lit8, Lit81, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit39, Lit57, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit28, Lit24, Lit5);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit41, "Déconnexion", Lit7);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit44, Lit82, Lit5);
        return runtime.setAndCoerceProperty$Ex(Lit80, Lit23, Lit24, Lit5);
    }

    public Object B6$Click() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit61, Lit84, LList.Empty, LList.Empty);
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen1"), Lit85, "open another screen");
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
        Screen2 = this;
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
