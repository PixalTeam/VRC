package appinventor.ai_kellislelouer47.Pixal;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AppInventorCompatActivity;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.ListView;
import com.google.appinventor.components.runtime.TinyDB;
import com.google.appinventor.components.runtime.VerticalArrangement;
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
import gnu.mapping.Procedure;
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

/* compiled from: Compte.yail */
public class Compte extends Form implements Runnable {
    public static Compte Compte;
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("Compte").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("getMessage").readResolve());
    static final PairWithPosition Lit10 = PairWithPosition.make(Lit145, PairWithPosition.make(Lit145, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41140), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41135);
    static final SimpleSymbol Lit100 = ((SimpleSymbol) new SimpleSymbol("SelectionColor").readResolve());
    static final IntNum Lit101;
    static final SimpleSymbol Lit102 = ((SimpleSymbol) new SimpleSymbol("TextSize").readResolve());
    static final IntNum Lit103 = IntNum.make(40);
    static final FString Lit104 = new FString("com.google.appinventor.components.runtime.ListView");
    static final FString Lit105 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit106 = ((SimpleSymbol) new SimpleSymbol("Button3").readResolve());
    static final IntNum Lit107 = IntNum.make(16777215);
    static final IntNum Lit108 = IntNum.make(20);
    static final IntNum Lit109;
    static final PairWithPosition Lit11 = PairWithPosition.make(Lit15, PairWithPosition.make(Lit145, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41367), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41361);
    static final FString Lit110 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit111 = ((SimpleSymbol) new SimpleSymbol("StoreValue").readResolve());
    static final PairWithPosition Lit112 = PairWithPosition.make(Lit15, PairWithPosition.make(Lit145, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 733278), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 733272);
    static final PairWithPosition Lit113 = PairWithPosition.make(Lit15, PairWithPosition.make(Lit145, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 733368), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 733362);
    static final PairWithPosition Lit114 = PairWithPosition.make(Lit15, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 733446);
    static final SimpleSymbol Lit115 = ((SimpleSymbol) new SimpleSymbol("Button3$Click").readResolve());
    static final FString Lit116 = new FString("com.google.appinventor.components.runtime.Web");
    static final FString Lit117 = new FString("com.google.appinventor.components.runtime.Web");
    static final SimpleSymbol Lit118 = ((SimpleSymbol) new SimpleSymbol("$responseContent").readResolve());
    static final PairWithPosition Lit119 = PairWithPosition.make(Lit15, PairWithPosition.make(Lit15, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 761979), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 761973);
    static final PairWithPosition Lit12 = PairWithPosition.make(Lit15, PairWithPosition.make(Lit15, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41382), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41376);
    static final PairWithPosition Lit120 = PairWithPosition.make(Lit144, PairWithPosition.make(Lit25, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 762185), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 762179);
    static final PairWithPosition Lit121 = PairWithPosition.make(Lit15, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 762215);
    static final PairWithPosition Lit122 = PairWithPosition.make(Lit144, PairWithPosition.make(Lit25, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 762429), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 762423);
    static final PairWithPosition Lit123;
    static final SimpleSymbol Lit124 = ((SimpleSymbol) new SimpleSymbol("ElementsFromString").readResolve());
    static final PairWithPosition Lit125;
    static final SimpleSymbol Lit126 = ((SimpleSymbol) new SimpleSymbol("Web1$GotText").readResolve());
    static final SimpleSymbol Lit127 = ((SimpleSymbol) new SimpleSymbol("GotText").readResolve());
    static final FString Lit128 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final FString Lit129 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("Web1").readResolve());
    static final SimpleSymbol Lit130 = ((SimpleSymbol) new SimpleSymbol("get-simple-name").readResolve());
    static final SimpleSymbol Lit131 = ((SimpleSymbol) new SimpleSymbol("android-log-form").readResolve());
    static final SimpleSymbol Lit132 = ((SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve());
    static final SimpleSymbol Lit133 = ((SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve());
    static final SimpleSymbol Lit134 = ((SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve());
    static final SimpleSymbol Lit135 = ((SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve());
    static final SimpleSymbol Lit136 = ((SimpleSymbol) new SimpleSymbol("add-to-events").readResolve());
    static final SimpleSymbol Lit137 = ((SimpleSymbol) new SimpleSymbol("add-to-components").readResolve());
    static final SimpleSymbol Lit138 = ((SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve());
    static final SimpleSymbol Lit139 = ((SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve());
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("Url").readResolve());
    static final SimpleSymbol Lit140 = ((SimpleSymbol) new SimpleSymbol("send-error").readResolve());
    static final SimpleSymbol Lit141 = ((SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve());
    static final SimpleSymbol Lit142 = ((SimpleSymbol) new SimpleSymbol("dispatchGenericEvent").readResolve());
    static final SimpleSymbol Lit143 = ((SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve());
    static final SimpleSymbol Lit144 = ((SimpleSymbol) new SimpleSymbol("list").readResolve());
    static final SimpleSymbol Lit145 = ((SimpleSymbol) new SimpleSymbol("any").readResolve());
    static final SimpleSymbol Lit15;
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("Get").readResolve());
    static final PairWithPosition Lit17 = PairWithPosition.make(Lit15, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41599);
    static final PairWithPosition Lit18 = PairWithPosition.make(Lit15, PairWithPosition.make(Lit145, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41125), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41119);
    static final PairWithPosition Lit19 = PairWithPosition.make(Lit145, PairWithPosition.make(Lit145, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41140), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41135);
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve());
    static final PairWithPosition Lit20 = PairWithPosition.make(Lit15, PairWithPosition.make(Lit145, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41367), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41361);
    static final PairWithPosition Lit21 = PairWithPosition.make(Lit15, PairWithPosition.make(Lit15, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41382), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41376);
    static final PairWithPosition Lit22 = PairWithPosition.make(Lit15, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41599);
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("AccentColor").readResolve());
    static final IntNum Lit24;
    static final SimpleSymbol Lit25;
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("AppName").readResolve());
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve());
    static final IntNum Lit28;
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("PrimaryColor").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("g$URL").readResolve());
    static final IntNum Lit30;
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol("PrimaryColorDark").readResolve());
    static final IntNum Lit32;
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol("ShowListsAsJson").readResolve());
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve());
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("ShowStatusBar").readResolve());
    static final SimpleSymbol Lit36 = ((SimpleSymbol) new SimpleSymbol("Sizing").readResolve());
    static final SimpleSymbol Lit37 = ((SimpleSymbol) new SimpleSymbol("Title").readResolve());
    static final SimpleSymbol Lit38 = ((SimpleSymbol) new SimpleSymbol("TitleVisible").readResolve());
    static final SimpleSymbol Lit39 = ((SimpleSymbol) new SimpleSymbol("Compte$Initialize").readResolve());
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("g$values").readResolve());
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("Initialize").readResolve());
    static final FString Lit41 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement2").readResolve());
    static final IntNum Lit43 = IntNum.make(16777215);
    static final SimpleSymbol Lit44 = ((SimpleSymbol) new SimpleSymbol("Width").readResolve());
    static final IntNum Lit45 = IntNum.make(-2);
    static final FString Lit46 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit47 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit48 = ((SimpleSymbol) new SimpleSymbol("Button2").readResolve());
    static final SimpleSymbol Lit49 = ((SimpleSymbol) new SimpleSymbol("Height").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("p$getInfos").readResolve());
    static final IntNum Lit50 = IntNum.make(50);
    static final SimpleSymbol Lit51 = ((SimpleSymbol) new SimpleSymbol("Image").readResolve());
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("TextAlignment").readResolve());
    static final FString Lit53 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit54 = PairWithPosition.make(Lit15, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 204878);
    static final SimpleSymbol Lit55 = ((SimpleSymbol) new SimpleSymbol("Button2$Click").readResolve());
    static final SimpleSymbol Lit56 = ((SimpleSymbol) new SimpleSymbol("Click").readResolve());
    static final FString Lit57 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit58 = ((SimpleSymbol) new SimpleSymbol("Button1").readResolve());
    static final IntNum Lit59 = IntNum.make(16777215);
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol(TinyDB.DEFAULT_NAMESPACE).readResolve());
    static final SimpleSymbol Lit60 = ((SimpleSymbol) new SimpleSymbol("FontBold").readResolve());
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("FontSize").readResolve());
    static final IntNum Lit62 = IntNum.make(25);
    static final SimpleSymbol Lit63 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final IntNum Lit64 = IntNum.make(2);
    static final SimpleSymbol Lit65 = ((SimpleSymbol) new SimpleSymbol("TextColor").readResolve());
    static final IntNum Lit66;
    static final FString Lit67 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit68 = ((SimpleSymbol) new SimpleSymbol("Button1$Click").readResolve());
    static final FString Lit69 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("GetValue").readResolve());
    static final SimpleSymbol Lit70 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement1").readResolve());
    static final SimpleSymbol Lit71 = ((SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve());
    static final IntNum Lit72 = IntNum.make(3);
    static final IntNum Lit73 = IntNum.make(16777215);
    static final FString Lit74 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit75 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit76 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement1").readResolve());
    static final IntNum Lit77 = IntNum.make(16777215);
    static final FString Lit78 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit79 = new FString("com.google.appinventor.components.runtime.Label");
    static final IntNum Lit8 = IntNum.make(0);
    static final SimpleSymbol Lit80 = ((SimpleSymbol) new SimpleSymbol("Label1").readResolve());
    static final IntNum Lit81 = IntNum.make(30);
    static final IntNum Lit82 = IntNum.make(1);
    static final IntNum Lit83;
    static final FString Lit84 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit85 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit86 = ((SimpleSymbol) new SimpleSymbol("Label2").readResolve());
    static final IntNum Lit87 = IntNum.make(17);
    static final IntNum Lit88;
    static final FString Lit89 = new FString("com.google.appinventor.components.runtime.Label");
    static final PairWithPosition Lit9 = PairWithPosition.make(Lit15, PairWithPosition.make(Lit145, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41125), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 41119);
    static final FString Lit90 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit91 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement3").readResolve());
    static final IntNum Lit92 = IntNum.make(16777215);
    static final FString Lit93 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit94 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit95 = ((SimpleSymbol) new SimpleSymbol("Label3").readResolve());
    static final IntNum Lit96;
    static final FString Lit97 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit98 = new FString("com.google.appinventor.components.runtime.ListView");
    static final SimpleSymbol Lit99 = ((SimpleSymbol) new SimpleSymbol("ListView1").readResolve());
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
    static final ModuleMethod lambda$Fn29 = null;
    static final ModuleMethod lambda$Fn3 = null;
    static final ModuleMethod lambda$Fn4 = null;
    static final ModuleMethod lambda$Fn5 = null;
    static final ModuleMethod lambda$Fn6 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn9 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public Button Button1;
    public final ModuleMethod Button1$Click;
    public Button Button2;
    public final ModuleMethod Button2$Click;
    public Button Button3;
    public final ModuleMethod Button3$Click;
    public final ModuleMethod Compte$Initialize;
    public HorizontalArrangement HorizontalArrangement1;
    public HorizontalArrangement HorizontalArrangement2;
    public HorizontalArrangement HorizontalArrangement3;
    public Label Label1;
    public Label Label2;
    public Label Label3;
    public ListView ListView1;
    public TinyDB TinyDB1;
    public VerticalArrangement VerticalArrangement1;
    public Web Web1;
    public final ModuleMethod Web1$GotText;
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

    /* compiled from: Compte.yail */
    public class frame extends ModuleBody {
        Compte $main = this;

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 18:
                    return Compte.lambda2();
                case 19:
                    this.$main.$define();
                    return Values.empty;
                case 20:
                    return Compte.lambda3();
                case 21:
                    return Compte.lambda4();
                case 22:
                    return Compte.lambda5();
                case 23:
                    return Compte.lambda7();
                case 24:
                    return Compte.lambda6();
                case 25:
                    return Compte.lambda8();
                case 26:
                    return this.$main.Compte$Initialize();
                case 27:
                    return Compte.lambda9();
                case 28:
                    return Compte.lambda10();
                case 29:
                    return Compte.lambda11();
                case 30:
                    return Compte.lambda12();
                case 31:
                    return this.$main.Button2$Click();
                case 32:
                    return Compte.lambda13();
                case 33:
                    return Compte.lambda14();
                case 34:
                    return this.$main.Button1$Click();
                case 35:
                    return Compte.lambda15();
                case 36:
                    return Compte.lambda16();
                case 37:
                    return Compte.lambda17();
                case 38:
                    return Compte.lambda18();
                case 39:
                    return Compte.lambda19();
                case 40:
                    return Compte.lambda20();
                case 41:
                    return Compte.lambda21();
                case 42:
                    return Compte.lambda22();
                case 43:
                    return Compte.lambda23();
                case 44:
                    return Compte.lambda24();
                case 45:
                    return Compte.lambda25();
                case 46:
                    return Compte.lambda26();
                case 47:
                    return Compte.lambda27();
                case 48:
                    return Compte.lambda28();
                case 49:
                    return Compte.lambda29();
                case 50:
                    return Compte.lambda30();
                case 51:
                    return this.$main.Button3$Click();
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
                case 50:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 51:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 1:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 2:
                    if (!(obj instanceof Compte)) {
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
                    if (!(obj instanceof Compte)) {
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
                    if (!(obj instanceof Compte)) {
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
                    if (!(obj instanceof Compte)) {
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
                case 52:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
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
                    Compte compte = this.$main;
                    try {
                        Component component = (Component) obj;
                        try {
                            String str = (String) obj2;
                            try {
                                if (obj3 == Boolean.FALSE) {
                                    z = false;
                                }
                                try {
                                    compte.dispatchGenericEvent(component, str, z, (Object[]) obj4);
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
                case 52:
                    return this.$main.Web1$GotText(obj, obj2, obj3, obj4);
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
    }

    static {
        SimpleSymbol simpleSymbol = Lit144;
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("number").readResolve();
        Lit25 = simpleSymbol2;
        Lit125 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol2, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 762628), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 762622);
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_TEXT).readResolve();
        Lit15 = simpleSymbol3;
        Lit123 = PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Compte.yail", 762459);
        int[] iArr = new int[2];
        iArr[0] = -16711681;
        Lit109 = IntNum.make(iArr);
        int[] iArr2 = new int[2];
        iArr2[0] = -16776961;
        Lit101 = IntNum.make(iArr2);
        int[] iArr3 = new int[2];
        iArr3[0] = -1;
        Lit96 = IntNum.make(iArr3);
        int[] iArr4 = new int[2];
        iArr4[0] = -1;
        Lit88 = IntNum.make(iArr4);
        int[] iArr5 = new int[2];
        iArr5[0] = -1;
        Lit83 = IntNum.make(iArr5);
        int[] iArr6 = new int[2];
        iArr6[0] = -1;
        Lit66 = IntNum.make(iArr6);
        int[] iArr7 = new int[2];
        iArr7[0] = -16777216;
        Lit32 = IntNum.make(iArr7);
        int[] iArr8 = new int[2];
        iArr8[0] = -16777216;
        Lit30 = IntNum.make(iArr8);
        int[] iArr9 = new int[2];
        iArr9[0] = -16777216;
        Lit28 = IntNum.make(iArr9);
        int[] iArr10 = new int[2];
        iArr10[0] = -16777216;
        Lit24 = IntNum.make(iArr10);
    }

    public Compte() {
        ModuleInfo.register(this);
        frame frame2 = new frame();
        this.get$Mnsimple$Mnname = new ModuleMethod(frame2, 1, Lit130, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.onCreate = new ModuleMethod(frame2, 2, "onCreate", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.android$Mnlog$Mnform = new ModuleMethod(frame2, 3, Lit131, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(frame2, 4, Lit132, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 5, Lit133, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 7, Lit134, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(frame2, 8, Lit135, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(frame2, 9, Lit136, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(frame2, 10, Lit137, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(frame2, 11, Lit138, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(frame2, 12, Lit139, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(frame2, 13, Lit140, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(frame2, 14, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(frame2, 15, Lit141, 16388);
        this.dispatchGenericEvent = new ModuleMethod(frame2, 16, Lit142, 16388);
        this.lookup$Mnhandler = new ModuleMethod(frame2, 17, Lit143, 8194);
        ModuleMethod moduleMethod = new ModuleMethod(frame2, 18, null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime7968961605135708732.scm:622");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(frame2, 19, "$define", 0);
        lambda$Fn2 = new ModuleMethod(frame2, 20, null, 0);
        lambda$Fn3 = new ModuleMethod(frame2, 21, null, 0);
        lambda$Fn4 = new ModuleMethod(frame2, 22, null, 0);
        lambda$Fn6 = new ModuleMethod(frame2, 23, null, 0);
        lambda$Fn5 = new ModuleMethod(frame2, 24, null, 0);
        lambda$Fn7 = new ModuleMethod(frame2, 25, null, 0);
        this.Compte$Initialize = new ModuleMethod(frame2, 26, Lit39, 0);
        lambda$Fn8 = new ModuleMethod(frame2, 27, null, 0);
        lambda$Fn9 = new ModuleMethod(frame2, 28, null, 0);
        lambda$Fn10 = new ModuleMethod(frame2, 29, null, 0);
        lambda$Fn11 = new ModuleMethod(frame2, 30, null, 0);
        this.Button2$Click = new ModuleMethod(frame2, 31, Lit55, 0);
        lambda$Fn12 = new ModuleMethod(frame2, 32, null, 0);
        lambda$Fn13 = new ModuleMethod(frame2, 33, null, 0);
        this.Button1$Click = new ModuleMethod(frame2, 34, Lit68, 0);
        lambda$Fn14 = new ModuleMethod(frame2, 35, null, 0);
        lambda$Fn15 = new ModuleMethod(frame2, 36, null, 0);
        lambda$Fn16 = new ModuleMethod(frame2, 37, null, 0);
        lambda$Fn17 = new ModuleMethod(frame2, 38, null, 0);
        lambda$Fn18 = new ModuleMethod(frame2, 39, null, 0);
        lambda$Fn19 = new ModuleMethod(frame2, 40, null, 0);
        lambda$Fn20 = new ModuleMethod(frame2, 41, null, 0);
        lambda$Fn21 = new ModuleMethod(frame2, 42, null, 0);
        lambda$Fn22 = new ModuleMethod(frame2, 43, null, 0);
        lambda$Fn23 = new ModuleMethod(frame2, 44, null, 0);
        lambda$Fn24 = new ModuleMethod(frame2, 45, null, 0);
        lambda$Fn25 = new ModuleMethod(frame2, 46, null, 0);
        lambda$Fn26 = new ModuleMethod(frame2, 47, null, 0);
        lambda$Fn27 = new ModuleMethod(frame2, 48, null, 0);
        lambda$Fn28 = new ModuleMethod(frame2, 49, null, 0);
        lambda$Fn29 = new ModuleMethod(frame2, 50, null, 0);
        this.Button3$Click = new ModuleMethod(frame2, 51, Lit115, 0);
        this.Web1$GotText = new ModuleMethod(frame2, 52, Lit126, 16388);
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
        Compte = null;
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
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit4, runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list")), $result);
        } else {
            addToGlobalVars(Lit4, lambda$Fn3);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit5, lambda$Fn4), $result);
        } else {
            addToGlobalVars(Lit5, lambda$Fn5);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit0, Lit23, Lit24, Lit25);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit26, "Pixal", Lit15);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit27, Lit28, Lit25);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit29, Lit30, Lit25);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit31, Lit32, Lit25);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit33, Boolean.FALSE, Lit34);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit35, Boolean.FALSE, Lit34);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit36, "Fixed", Lit15);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit37, "Compte", Lit15);
            Values.writeValues(runtime.setAndCoerceProperty$Ex(Lit0, Lit38, Boolean.FALSE, Lit34), $result);
        } else {
            addToFormDoAfterCreation(new Promise(lambda$Fn7));
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit39, this.Compte$Initialize);
        } else {
            addToFormEnvironment(Lit39, this.Compte$Initialize);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Compte", "Initialize");
        } else {
            addToEvents(Lit0, Lit40);
        }
        this.HorizontalArrangement2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit41, Lit42, lambda$Fn8), $result);
        } else {
            addToComponents(Lit0, Lit46, Lit42, lambda$Fn9);
        }
        this.Button2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit42, Lit47, Lit48, lambda$Fn10), $result);
        } else {
            addToComponents(Lit42, Lit53, Lit48, lambda$Fn11);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit55, this.Button2$Click);
        } else {
            addToFormEnvironment(Lit55, this.Button2$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button2", "Click");
        } else {
            addToEvents(Lit48, Lit56);
        }
        this.Button1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit42, Lit57, Lit58, lambda$Fn12), $result);
        } else {
            addToComponents(Lit42, Lit67, Lit58, lambda$Fn13);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit68, this.Button1$Click);
        } else {
            addToFormEnvironment(Lit68, this.Button1$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button1", "Click");
        } else {
            addToEvents(Lit58, Lit56);
        }
        this.HorizontalArrangement1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit69, Lit70, lambda$Fn14), $result);
        } else {
            addToComponents(Lit0, Lit74, Lit70, lambda$Fn15);
        }
        this.VerticalArrangement1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit70, Lit75, Lit76, lambda$Fn16), $result);
        } else {
            addToComponents(Lit70, Lit78, Lit76, lambda$Fn17);
        }
        this.Label1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit76, Lit79, Lit80, lambda$Fn18), $result);
        } else {
            addToComponents(Lit76, Lit84, Lit80, lambda$Fn19);
        }
        this.Label2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit76, Lit85, Lit86, lambda$Fn20), $result);
        } else {
            addToComponents(Lit76, Lit89, Lit86, lambda$Fn21);
        }
        this.HorizontalArrangement3 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit76, Lit90, Lit91, lambda$Fn22), $result);
        } else {
            addToComponents(Lit76, Lit93, Lit91, lambda$Fn23);
        }
        this.Label3 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit76, Lit94, Lit95, lambda$Fn24), $result);
        } else {
            addToComponents(Lit76, Lit97, Lit95, lambda$Fn25);
        }
        this.ListView1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit76, Lit98, Lit99, lambda$Fn26), $result);
        } else {
            addToComponents(Lit76, Lit104, Lit99, lambda$Fn27);
        }
        this.Button3 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit76, Lit105, Lit106, lambda$Fn28), $result);
        } else {
            addToComponents(Lit76, Lit110, Lit106, lambda$Fn29);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit115, this.Button3$Click);
        } else {
            addToFormEnvironment(Lit115, this.Button3$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Button3", "Click");
        } else {
            addToEvents(Lit106, Lit56);
        }
        this.Web1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit116, Lit13, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit117, Lit13, Boolean.FALSE);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit126, this.Web1$GotText);
        } else {
            addToFormEnvironment(Lit126, this.Web1$GotText);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Web1", "GotText");
        } else {
            addToEvents(Lit13, Lit127);
        }
        this.TinyDB1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit128, Lit6, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit129, Lit6, Boolean.FALSE);
        }
        runtime.initRuntime();
    }

    static String lambda3() {
        return "";
    }

    static Object lambda4() {
        return runtime.callYailPrimitive(runtime.make$Mnyail$Mnlist, LList.Empty, LList.Empty, "make a list");
    }

    static Object lambda5() {
        if (runtime.callYailPrimitive(runtime.yail$Mnnot$Mnequal$Qu, LList.list2(runtime.callComponentMethod(Lit6, Lit7, LList.list2("user_id", Lit8), Lit9), Lit8), Lit10, "=") == Boolean.FALSE) {
            return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen3"), Lit17, "open another screen");
        }
        runtime.addGlobalVarToCurrentFormEnvironment(Lit3, runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("http://vrc.kellis.fr/android/actions/compte.php?id=", runtime.callComponentMethod(Lit6, Lit7, LList.list2("user_id", Lit8), Lit11)), Lit12, "join"));
        runtime.setAndCoerceProperty$Ex(Lit13, Lit14, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St), Lit15);
        return runtime.callComponentMethod(Lit13, Lit16, LList.Empty, LList.Empty);
    }

    static Procedure lambda6() {
        return lambda$Fn6;
    }

    static Object lambda7() {
        if (runtime.callYailPrimitive(runtime.yail$Mnnot$Mnequal$Qu, LList.list2(runtime.callComponentMethod(Lit6, Lit7, LList.list2("user_id", Lit8), Lit18), Lit8), Lit19, "=") == Boolean.FALSE) {
            return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen3"), Lit22, "open another screen");
        }
        runtime.addGlobalVarToCurrentFormEnvironment(Lit3, runtime.callYailPrimitive(strings.string$Mnappend, LList.list2("http://vrc.kellis.fr/android/actions/compte.php?id=", runtime.callComponentMethod(Lit6, Lit7, LList.list2("user_id", Lit8), Lit20)), Lit21, "join"));
        runtime.setAndCoerceProperty$Ex(Lit13, Lit14, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St), Lit15);
        return runtime.callComponentMethod(Lit13, Lit16, LList.Empty, LList.Empty);
    }

    static Object lambda8() {
        runtime.setAndCoerceProperty$Ex(Lit0, Lit23, Lit24, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit26, "Pixal", Lit15);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit27, Lit28, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit29, Lit30, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit31, Lit32, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit33, Boolean.FALSE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit35, Boolean.FALSE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit36, "Fixed", Lit15);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit37, "Compte", Lit15);
        return runtime.setAndCoerceProperty$Ex(Lit0, Lit38, Boolean.FALSE, Lit34);
    }

    public Object Compte$Initialize() {
        runtime.setThisForm();
        return Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit5, runtime.$Stthe$Mnnull$Mnvalue$St));
    }

    static Object lambda10() {
        runtime.setAndCoerceProperty$Ex(Lit42, Lit27, Lit43, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit42, Lit44, Lit45, Lit25);
    }

    static Object lambda9() {
        runtime.setAndCoerceProperty$Ex(Lit42, Lit27, Lit43, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit42, Lit44, Lit45, Lit25);
    }

    static Object lambda11() {
        runtime.setAndCoerceProperty$Ex(Lit48, Lit49, Lit50, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit48, Lit51, "56673328.png", Lit15);
        runtime.setAndCoerceProperty$Ex(Lit48, Lit52, Lit8, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit48, Lit44, Lit50, Lit25);
    }

    static Object lambda12() {
        runtime.setAndCoerceProperty$Ex(Lit48, Lit49, Lit50, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit48, Lit51, "56673328.png", Lit15);
        runtime.setAndCoerceProperty$Ex(Lit48, Lit52, Lit8, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit48, Lit44, Lit50, Lit25);
    }

    public Object Button2$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen1"), Lit54, "open another screen");
    }

    static Object lambda13() {
        runtime.setAndCoerceProperty$Ex(Lit58, Lit27, Lit59, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit58, Lit60, Boolean.TRUE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit58, Lit61, Lit62, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit58, Lit63, "Actualiser", Lit15);
        runtime.setAndCoerceProperty$Ex(Lit58, Lit52, Lit64, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit58, Lit65, Lit66, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit58, Lit44, Lit45, Lit25);
    }

    static Object lambda14() {
        runtime.setAndCoerceProperty$Ex(Lit58, Lit27, Lit59, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit58, Lit60, Boolean.TRUE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit58, Lit61, Lit62, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit58, Lit63, "Actualiser", Lit15);
        runtime.setAndCoerceProperty$Ex(Lit58, Lit52, Lit64, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit58, Lit65, Lit66, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit58, Lit44, Lit45, Lit25);
    }

    public Object Button1$Click() {
        runtime.setThisForm();
        return Scheme.applyToArgs.apply1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit5, runtime.$Stthe$Mnnull$Mnvalue$St));
    }

    static Object lambda15() {
        runtime.setAndCoerceProperty$Ex(Lit70, Lit71, Lit72, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit70, Lit27, Lit73, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit70, Lit49, Lit45, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit70, Lit44, Lit45, Lit25);
    }

    static Object lambda16() {
        runtime.setAndCoerceProperty$Ex(Lit70, Lit71, Lit72, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit70, Lit27, Lit73, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit70, Lit49, Lit45, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit70, Lit44, Lit45, Lit25);
    }

    static Object lambda17() {
        runtime.setAndCoerceProperty$Ex(Lit76, Lit71, Lit72, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit76, Lit27, Lit77, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit76, Lit49, Lit45, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit76, Lit44, Lit45, Lit25);
    }

    static Object lambda18() {
        runtime.setAndCoerceProperty$Ex(Lit76, Lit71, Lit72, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit76, Lit27, Lit77, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit76, Lit49, Lit45, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit76, Lit44, Lit45, Lit25);
    }

    static Object lambda19() {
        runtime.setAndCoerceProperty$Ex(Lit80, Lit60, Boolean.TRUE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit61, Lit81, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit63, "PSEUDO", Lit15);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit52, Lit82, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit80, Lit65, Lit83, Lit25);
    }

    static Object lambda20() {
        runtime.setAndCoerceProperty$Ex(Lit80, Lit60, Boolean.TRUE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit61, Lit81, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit63, "PSEUDO", Lit15);
        runtime.setAndCoerceProperty$Ex(Lit80, Lit52, Lit82, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit80, Lit65, Lit83, Lit25);
    }

    static Object lambda21() {
        runtime.setAndCoerceProperty$Ex(Lit86, Lit60, Boolean.TRUE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit86, Lit61, Lit87, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit86, Lit63, "SINCE", Lit15);
        runtime.setAndCoerceProperty$Ex(Lit86, Lit52, Lit82, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit86, Lit65, Lit88, Lit25);
    }

    static Object lambda22() {
        runtime.setAndCoerceProperty$Ex(Lit86, Lit60, Boolean.TRUE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit86, Lit61, Lit87, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit86, Lit63, "SINCE", Lit15);
        runtime.setAndCoerceProperty$Ex(Lit86, Lit52, Lit82, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit86, Lit65, Lit88, Lit25);
    }

    static Object lambda23() {
        runtime.setAndCoerceProperty$Ex(Lit91, Lit27, Lit92, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit91, Lit49, Lit62, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit91, Lit44, Lit45, Lit25);
    }

    static Object lambda24() {
        runtime.setAndCoerceProperty$Ex(Lit91, Lit27, Lit92, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit91, Lit49, Lit62, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit91, Lit44, Lit45, Lit25);
    }

    static Object lambda25() {
        runtime.setAndCoerceProperty$Ex(Lit95, Lit63, "Liste de vos VRC:", Lit15);
        return runtime.setAndCoerceProperty$Ex(Lit95, Lit65, Lit96, Lit25);
    }

    static Object lambda26() {
        runtime.setAndCoerceProperty$Ex(Lit95, Lit63, "Liste de vos VRC:", Lit15);
        return runtime.setAndCoerceProperty$Ex(Lit95, Lit65, Lit96, Lit25);
    }

    static Object lambda27() {
        runtime.setAndCoerceProperty$Ex(Lit99, Lit49, Lit45, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit99, Lit100, Lit101, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit99, Lit102, Lit103, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit99, Lit44, Lit45, Lit25);
    }

    static Object lambda28() {
        runtime.setAndCoerceProperty$Ex(Lit99, Lit49, Lit45, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit99, Lit100, Lit101, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit99, Lit102, Lit103, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit99, Lit44, Lit45, Lit25);
    }

    static Object lambda29() {
        runtime.setAndCoerceProperty$Ex(Lit106, Lit27, Lit107, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit106, Lit60, Boolean.TRUE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit106, Lit61, Lit108, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit106, Lit63, "Deconnexion", Lit15);
        runtime.setAndCoerceProperty$Ex(Lit106, Lit65, Lit109, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit106, Lit44, Lit45, Lit25);
    }

    static Object lambda30() {
        runtime.setAndCoerceProperty$Ex(Lit106, Lit27, Lit107, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit106, Lit60, Boolean.TRUE, Lit34);
        runtime.setAndCoerceProperty$Ex(Lit106, Lit61, Lit108, Lit25);
        runtime.setAndCoerceProperty$Ex(Lit106, Lit63, "Deconnexion", Lit15);
        runtime.setAndCoerceProperty$Ex(Lit106, Lit65, Lit109, Lit25);
        return runtime.setAndCoerceProperty$Ex(Lit106, Lit44, Lit45, Lit25);
    }

    public Object Button3$Click() {
        runtime.setThisForm();
        runtime.callComponentMethod(Lit6, Lit111, LList.list2("isConnected", Lit8), Lit112);
        runtime.callComponentMethod(Lit6, Lit111, LList.list2("user_id", Lit8), Lit113);
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen1"), Lit114, "open another screen");
    }

    public Object Web1$GotText(Object $url, Object $responseCode, Object $responseType, Object $responseContent) {
        runtime.sanitizeComponentData($url);
        runtime.sanitizeComponentData($responseCode);
        runtime.sanitizeComponentData($responseType);
        Object $responseContent2 = runtime.sanitizeComponentData($responseContent);
        runtime.setThisForm();
        SimpleSymbol simpleSymbol = Lit4;
        ModuleMethod moduleMethod = runtime.string$Mnsplit;
        if ($responseContent2 instanceof Package) {
            $responseContent2 = runtime.signalRuntimeError(strings.stringAppend("The variable ", runtime.getDisplayRepresentation(Lit118), " is not bound in the current context"), "Unbound Variable");
        }
        runtime.addGlobalVarToCurrentFormEnvironment(simpleSymbol, runtime.callYailPrimitive(moduleMethod, LList.list2($responseContent2, "%"), Lit119, "split"));
        runtime.setAndCoerceProperty$Ex(Lit80, Lit63, runtime.callYailPrimitive(runtime.string$Mnto$Mnupper$Mncase, LList.list1(runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St), Lit82), Lit120, "select list item")), Lit121, "upcase"), Lit15);
        runtime.setAndCoerceProperty$Ex(Lit86, Lit63, runtime.callYailPrimitive(runtime.string$Mnto$Mnupper$Mncase, LList.list1(runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St), Lit64), Lit122, "select list item")), Lit123, "upcase"), Lit15);
        return runtime.setAndCoerceProperty$Ex(Lit99, Lit124, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St), Lit72), Lit125, "select list item"), Lit15);
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
        Compte = this;
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
