package appinventor.ai_gumbraise.Pixal;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AppInventorCompatActivity;
import com.google.appinventor.components.runtime.BluetoothClient;
import com.google.appinventor.components.runtime.Button;
import com.google.appinventor.components.runtime.Clock;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.HandlesEventDispatching;
import com.google.appinventor.components.runtime.HorizontalArrangement;
import com.google.appinventor.components.runtime.HorizontalScrollArrangement;
import com.google.appinventor.components.runtime.Label;
import com.google.appinventor.components.runtime.TableArrangement;
import com.google.appinventor.components.runtime.TinyDB;
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
import kawa.standard.require;

/* compiled from: Screen1.yail */
public class Screen1 extends Form implements Runnable {
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("Screen1").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("getMessage").readResolve());
    static final SimpleSymbol Lit10;
    static final SimpleSymbol Lit100 = ((SimpleSymbol) new SimpleSymbol("HorizontalScrollArrangement2").readResolve());
    static final IntNum Lit101;
    static final FString Lit102 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit103 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol("ValAccel").readResolve());
    static final IntNum Lit105;
    static final IntNum Lit106 = IntNum.make(18);
    static final IntNum Lit107;
    static final FString Lit108 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit109 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("AppName").readResolve());
    static final SimpleSymbol Lit110 = ((SimpleSymbol) new SimpleSymbol("HorizontalScrollArrangement12").readResolve());
    static final IntNum Lit111;
    static final FString Lit112 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit113 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit114 = ((SimpleSymbol) new SimpleSymbol("Label9").readResolve());
    static final IntNum Lit115;
    static final FString Lit116 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit117 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit118 = ((SimpleSymbol) new SimpleSymbol("HorizontalScrollArrangement3").readResolve());
    static final IntNum Lit119;
    static final SimpleSymbol Lit12;
    static final FString Lit120 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit121 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit122 = ((SimpleSymbol) new SimpleSymbol("ValRégi").readResolve());
    static final IntNum Lit123;
    static final FString Lit124 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit125 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit126 = ((SimpleSymbol) new SimpleSymbol("HorizontalScrollArrangement11").readResolve());
    static final IntNum Lit127;
    static final FString Lit128 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit129 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve());
    static final SimpleSymbol Lit130 = ((SimpleSymbol) new SimpleSymbol("A17").readResolve());
    static final IntNum Lit131;
    static final IntNum Lit132;
    static final FString Lit133 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit134 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit135 = ((SimpleSymbol) new SimpleSymbol("HorizontalScrollArrangement10").readResolve());
    static final IntNum Lit136;
    static final FString Lit137 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit138 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit139 = ((SimpleSymbol) new SimpleSymbol("ValTemp").readResolve());
    static final IntNum Lit14;
    static final IntNum Lit140;
    static final IntNum Lit141;
    static final FString Lit142 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit143 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit144 = ((SimpleSymbol) new SimpleSymbol("HorizontalScrollArrangement5").readResolve());
    static final IntNum Lit145;
    static final FString Lit146 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit147 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit148 = ((SimpleSymbol) new SimpleSymbol("Label8").readResolve());
    static final IntNum Lit149;
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("Icon").readResolve());
    static final IntNum Lit150;
    static final FString Lit151 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit152 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit153 = ((SimpleSymbol) new SimpleSymbol("HorizontalScrollArrangement14").readResolve());
    static final IntNum Lit154;
    static final FString Lit155 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit156 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit157 = ((SimpleSymbol) new SimpleSymbol("Label2").readResolve());
    static final IntNum Lit158;
    static final IntNum Lit159;
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("OpenScreenAnimation").readResolve());
    static final FString Lit160 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit161 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit162 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement1").readResolve());
    static final IntNum Lit163;
    static final FString Lit164 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit165 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit166 = ((SimpleSymbol) new SimpleSymbol("ValNive").readResolve());
    static final IntNum Lit167;
    static final IntNum Lit168;
    static final FString Lit169 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("PrimaryColor").readResolve());
    static final FString Lit170 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit171 = ((SimpleSymbol) new SimpleSymbol("HorizontalScrollArrangement13").readResolve());
    static final IntNum Lit172;
    static final FString Lit173 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit174 = new FString("com.google.appinventor.components.runtime.BluetoothClient");
    static final SimpleSymbol Lit175 = ((SimpleSymbol) new SimpleSymbol("BluetoothClient1").readResolve());
    static final FString Lit176 = new FString("com.google.appinventor.components.runtime.BluetoothClient");
    static final FString Lit177 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final SimpleSymbol Lit178 = ((SimpleSymbol) new SimpleSymbol(TinyDB.DEFAULT_NAMESPACE).readResolve());
    static final FString Lit179 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final IntNum Lit18;
    static final FString Lit180 = new FString("com.google.appinventor.components.runtime.Clock");
    static final FString Lit181 = new FString("com.google.appinventor.components.runtime.Clock");
    static final SimpleSymbol Lit182 = ((SimpleSymbol) new SimpleSymbol("BytesAvailableToReceive").readResolve());
    static final PairWithPosition Lit183 = PairWithPosition.make(Lit10, PairWithPosition.make(Lit10, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1659036), "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1659028);
    static final SimpleSymbol Lit184 = ((SimpleSymbol) new SimpleSymbol("ReceiveText").readResolve());
    static final PairWithPosition Lit185 = PairWithPosition.make(Lit10, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1659244);
    static final PairWithPosition Lit186 = PairWithPosition.make(Lit12, PairWithPosition.make(Lit12, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1659416), "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1659410);
    static final PairWithPosition Lit187 = PairWithPosition.make(Lit216, PairWithPosition.make(Lit10, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1659441), "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1659435);
    static final PairWithPosition Lit188 = PairWithPosition.make(Lit12, PairWithPosition.make(Lit12, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1659630), "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1659624);
    static final PairWithPosition Lit189 = PairWithPosition.make(Lit216, PairWithPosition.make(Lit10, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1659655), "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1659649);
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("PrimaryColorDark").readResolve());
    static final PairWithPosition Lit190 = PairWithPosition.make(Lit12, PairWithPosition.make(Lit12, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1659844), "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1659838);
    static final IntNum Lit191 = IntNum.make(3);
    static final PairWithPosition Lit192 = PairWithPosition.make(Lit216, PairWithPosition.make(Lit10, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1659869), "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1659863);
    static final PairWithPosition Lit193 = PairWithPosition.make(Lit12, PairWithPosition.make(Lit12, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1660058), "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1660052);
    static final IntNum Lit194 = IntNum.make(4);
    static final PairWithPosition Lit195;
    static final PairWithPosition Lit196 = PairWithPosition.make(Lit12, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1660187);
    static final PairWithPosition Lit197 = PairWithPosition.make(Lit12, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1660362);
    static final PairWithPosition Lit198 = PairWithPosition.make(Lit12, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1660535);
    static final PairWithPosition Lit199;
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve());
    static final IntNum Lit20;
    static final SimpleSymbol Lit200 = ((SimpleSymbol) new SimpleSymbol("Clock1$Timer").readResolve());
    static final SimpleSymbol Lit201 = ((SimpleSymbol) new SimpleSymbol("Timer").readResolve());
    static final SimpleSymbol Lit202 = ((SimpleSymbol) new SimpleSymbol("get-simple-name").readResolve());
    static final SimpleSymbol Lit203 = ((SimpleSymbol) new SimpleSymbol("android-log-form").readResolve());
    static final SimpleSymbol Lit204 = ((SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve());
    static final SimpleSymbol Lit205 = ((SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve());
    static final SimpleSymbol Lit206 = ((SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve());
    static final SimpleSymbol Lit207 = ((SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve());
    static final SimpleSymbol Lit208 = ((SimpleSymbol) new SimpleSymbol("add-to-events").readResolve());
    static final SimpleSymbol Lit209 = ((SimpleSymbol) new SimpleSymbol("add-to-components").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("Scrollable").readResolve());
    static final SimpleSymbol Lit210 = ((SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve());
    static final SimpleSymbol Lit211 = ((SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve());
    static final SimpleSymbol Lit212 = ((SimpleSymbol) new SimpleSymbol("send-error").readResolve());
    static final SimpleSymbol Lit213 = ((SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve());
    static final SimpleSymbol Lit214 = ((SimpleSymbol) new SimpleSymbol("dispatchGenericEvent").readResolve());
    static final SimpleSymbol Lit215 = ((SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve());
    static final SimpleSymbol Lit216 = ((SimpleSymbol) new SimpleSymbol("list").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("ShowListsAsJson").readResolve());
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("Sizing").readResolve());
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("Title").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("TitleVisible").readResolve());
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("Clock1").readResolve());
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("TimerEnabled").readResolve());
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("TimerInterval").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("g$Trame").readResolve());
    static final IntNum Lit30 = IntNum.make(500);
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol("Screen1$Initialize").readResolve());
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol("Initialize").readResolve());
    static final FString Lit33 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("A1").readResolve());
    static final IntNum Lit35;
    static final SimpleSymbol Lit36 = ((SimpleSymbol) new SimpleSymbol("Width").readResolve());
    static final IntNum Lit37 = IntNum.make(-2);
    static final FString Lit38 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit39 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("g$Accel").readResolve());
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("A2").readResolve());
    static final SimpleSymbol Lit41 = ((SimpleSymbol) new SimpleSymbol("Height").readResolve());
    static final IntNum Lit42 = IntNum.make(50);
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("Image").readResolve());
    static final FString Lit44 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit45 = PairWithPosition.make(Lit12, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 213070);
    static final SimpleSymbol Lit46 = ((SimpleSymbol) new SimpleSymbol("A2$Click").readResolve());
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("Click").readResolve());
    static final FString Lit48 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit49 = ((SimpleSymbol) new SimpleSymbol("A3").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("g$Régi").readResolve());
    static final IntNum Lit50;
    static final SimpleSymbol Lit51 = ((SimpleSymbol) new SimpleSymbol("FontBold").readResolve());
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("FontSize").readResolve());
    static final IntNum Lit53 = IntNum.make(25);
    static final SimpleSymbol Lit54 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final SimpleSymbol Lit55 = ((SimpleSymbol) new SimpleSymbol("TextAlignment").readResolve());
    static final IntNum Lit56 = IntNum.make(2);
    static final SimpleSymbol Lit57 = ((SimpleSymbol) new SimpleSymbol("TextColor").readResolve());
    static final IntNum Lit58;
    static final FString Lit59 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("g$Temp").readResolve());
    static final PairWithPosition Lit60 = PairWithPosition.make(Lit12, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 299086);
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("A3$Click").readResolve());
    static final FString Lit62 = new FString("com.google.appinventor.components.runtime.TableArrangement");
    static final SimpleSymbol Lit63 = ((SimpleSymbol) new SimpleSymbol("A4").readResolve());
    static final FString Lit64 = new FString("com.google.appinventor.components.runtime.TableArrangement");
    static final FString Lit65 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit66 = ((SimpleSymbol) new SimpleSymbol("A5").readResolve());
    static final SimpleSymbol Lit67 = ((SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve());
    static final SimpleSymbol Lit68 = ((SimpleSymbol) new SimpleSymbol("Column").readResolve());
    static final IntNum Lit69 = IntNum.make(1);
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("g$Nive").readResolve());
    static final SimpleSymbol Lit70 = ((SimpleSymbol) new SimpleSymbol("Row").readResolve());
    static final IntNum Lit71 = IntNum.make(0);
    static final IntNum Lit72 = IntNum.make(-1050);
    static final FString Lit73 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit74 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit75 = ((SimpleSymbol) new SimpleSymbol("A6").readResolve());
    static final IntNum Lit76;
    static final IntNum Lit77 = IntNum.make(20);
    static final IntNum Lit78;
    static final FString Lit79 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("AccentColor").readResolve());
    static final PairWithPosition Lit80 = PairWithPosition.make(Lit12, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 450638);
    static final SimpleSymbol Lit81 = ((SimpleSymbol) new SimpleSymbol("A6$Click").readResolve());
    static final FString Lit82 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit83 = ((SimpleSymbol) new SimpleSymbol("A9").readResolve());
    static final IntNum Lit84 = IntNum.make(-1050);
    static final FString Lit85 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit86 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit87 = ((SimpleSymbol) new SimpleSymbol("A10").readResolve());
    static final IntNum Lit88;
    static final SimpleSymbol Lit89 = ((SimpleSymbol) new SimpleSymbol("Shape").readResolve());
    static final IntNum Lit9;
    static final IntNum Lit90;
    static final FString Lit91 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit92 = PairWithPosition.make(Lit12, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 581710);
    static final SimpleSymbol Lit93 = ((SimpleSymbol) new SimpleSymbol("A10$Click").readResolve());
    static final FString Lit94 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit95 = ((SimpleSymbol) new SimpleSymbol("A15").readResolve());
    static final IntNum Lit96;
    static final IntNum Lit97;
    static final FString Lit98 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit99 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    public static Screen1 Screen1;
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
    static final ModuleMethod lambda$Fn30 = null;
    static final ModuleMethod lambda$Fn31 = null;
    static final ModuleMethod lambda$Fn32 = null;
    static final ModuleMethod lambda$Fn33 = null;
    static final ModuleMethod lambda$Fn34 = null;
    static final ModuleMethod lambda$Fn35 = null;
    static final ModuleMethod lambda$Fn36 = null;
    static final ModuleMethod lambda$Fn37 = null;
    static final ModuleMethod lambda$Fn38 = null;
    static final ModuleMethod lambda$Fn39 = null;
    static final ModuleMethod lambda$Fn4 = null;
    static final ModuleMethod lambda$Fn40 = null;
    static final ModuleMethod lambda$Fn41 = null;
    static final ModuleMethod lambda$Fn42 = null;
    static final ModuleMethod lambda$Fn43 = null;
    static final ModuleMethod lambda$Fn44 = null;
    static final ModuleMethod lambda$Fn45 = null;
    static final ModuleMethod lambda$Fn46 = null;
    static final ModuleMethod lambda$Fn47 = null;
    static final ModuleMethod lambda$Fn48 = null;
    static final ModuleMethod lambda$Fn49 = null;
    static final ModuleMethod lambda$Fn5 = null;
    static final ModuleMethod lambda$Fn50 = null;
    static final ModuleMethod lambda$Fn51 = null;
    static final ModuleMethod lambda$Fn52 = null;
    static final ModuleMethod lambda$Fn53 = null;
    static final ModuleMethod lambda$Fn54 = null;
    static final ModuleMethod lambda$Fn55 = null;
    static final ModuleMethod lambda$Fn56 = null;
    static final ModuleMethod lambda$Fn57 = null;
    static final ModuleMethod lambda$Fn58 = null;
    static final ModuleMethod lambda$Fn59 = null;
    static final ModuleMethod lambda$Fn6 = null;
    static final ModuleMethod lambda$Fn7 = null;
    static final ModuleMethod lambda$Fn8 = null;
    static final ModuleMethod lambda$Fn9 = null;
    public Boolean $Stdebug$Mnform$St;
    public final ModuleMethod $define;
    public HorizontalScrollArrangement A1;
    public Button A10;
    public final ModuleMethod A10$Click;
    public Label A15;
    public Label A17;
    public Button A2;
    public final ModuleMethod A2$Click;
    public Button A3;
    public final ModuleMethod A3$Click;
    public TableArrangement A4;
    public HorizontalArrangement A5;
    public Button A6;
    public final ModuleMethod A6$Click;
    public HorizontalArrangement A9;
    public BluetoothClient BluetoothClient1;
    public Clock Clock1;
    public final ModuleMethod Clock1$Timer;
    public HorizontalArrangement HorizontalArrangement1;
    public HorizontalScrollArrangement HorizontalScrollArrangement10;
    public HorizontalScrollArrangement HorizontalScrollArrangement11;
    public HorizontalScrollArrangement HorizontalScrollArrangement12;
    public HorizontalScrollArrangement HorizontalScrollArrangement13;
    public HorizontalScrollArrangement HorizontalScrollArrangement14;
    public HorizontalScrollArrangement HorizontalScrollArrangement2;
    public HorizontalScrollArrangement HorizontalScrollArrangement3;
    public HorizontalScrollArrangement HorizontalScrollArrangement5;
    public Label Label2;
    public Label Label8;
    public Label Label9;
    public final ModuleMethod Screen1$Initialize;
    public TinyDB TinyDB1;
    public Label ValAccel;
    public Label ValNive;

    /* renamed from: ValRégi reason: contains not printable characters */
    public Label f0ValRgi;
    public Label ValTemp;
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

    /* compiled from: Screen1.yail */
    public class frame extends ModuleBody {
        Screen1 $main = this;

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 1:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 2:
                    if (!(obj instanceof Screen1)) {
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
                    if (!(obj instanceof Screen1)) {
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
                    if (!(obj instanceof Screen1)) {
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
                    if (!(obj instanceof Screen1)) {
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
                    Screen1 screen1 = this.$main;
                    try {
                        Component component = (Component) obj;
                        try {
                            String str = (String) obj2;
                            try {
                                if (obj3 == Boolean.FALSE) {
                                    z = false;
                                }
                                try {
                                    screen1.dispatchGenericEvent(component, str, z, (Object[]) obj4);
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
                    return Screen1.lambda2();
                case 19:
                    this.$main.$define();
                    return Values.empty;
                case 20:
                    return Screen1.lambda3();
                case 21:
                    return Screen1.lambda4();
                case 22:
                    return Screen1.lambda5();
                case 23:
                    return Screen1.lambda6();
                case 24:
                    return Screen1.lambda7();
                case 25:
                    return Screen1.lambda8();
                case 26:
                    return this.$main.Screen1$Initialize();
                case 27:
                    return Screen1.lambda9();
                case 28:
                    return Screen1.lambda10();
                case 29:
                    return Screen1.lambda11();
                case 30:
                    return Screen1.lambda12();
                case 31:
                    return this.$main.A2$Click();
                case 32:
                    return Screen1.lambda13();
                case 33:
                    return Screen1.lambda14();
                case 34:
                    return this.$main.A3$Click();
                case 35:
                    return Screen1.lambda15();
                case 36:
                    return Screen1.lambda16();
                case 37:
                    return Screen1.lambda17();
                case 38:
                    return Screen1.lambda18();
                case 39:
                    return Screen1.lambda19();
                case 40:
                    return Screen1.lambda20();
                case 41:
                    return this.$main.A6$Click();
                case 42:
                    return Screen1.lambda21();
                case 43:
                    return Screen1.lambda22();
                case 44:
                    return Screen1.lambda23();
                case 45:
                    return Screen1.lambda24();
                case 46:
                    return this.$main.A10$Click();
                case 47:
                    return Screen1.lambda25();
                case 48:
                    return Screen1.lambda26();
                case 49:
                    return Screen1.lambda27();
                case 50:
                    return Screen1.lambda28();
                case 51:
                    return Screen1.lambda29();
                case 52:
                    return Screen1.lambda30();
                case 53:
                    return Screen1.lambda31();
                case 54:
                    return Screen1.lambda32();
                case 55:
                    return Screen1.lambda33();
                case 56:
                    return Screen1.lambda34();
                case 57:
                    return Screen1.lambda35();
                case 58:
                    return Screen1.lambda36();
                case 59:
                    return Screen1.lambda37();
                case 60:
                    return Screen1.lambda38();
                case 61:
                    return Screen1.lambda39();
                case 62:
                    return Screen1.lambda40();
                case 63:
                    return Screen1.lambda41();
                case 64:
                    return Screen1.lambda42();
                case 65:
                    return Screen1.lambda43();
                case 66:
                    return Screen1.lambda44();
                case 67:
                    return Screen1.lambda45();
                case 68:
                    return Screen1.lambda46();
                case 69:
                    return Screen1.lambda47();
                case 70:
                    return Screen1.lambda48();
                case 71:
                    return Screen1.lambda49();
                case 72:
                    return Screen1.lambda50();
                case 73:
                    return Screen1.lambda51();
                case 74:
                    return Screen1.lambda52();
                case 75:
                    return Screen1.lambda53();
                case 76:
                    return Screen1.lambda54();
                case 77:
                    return Screen1.lambda55();
                case 78:
                    return Screen1.lambda56();
                case 79:
                    return Screen1.lambda57();
                case 80:
                    return Screen1.lambda58();
                case 81:
                    return Screen1.lambda59();
                case 82:
                    return Screen1.lambda60();
                case 83:
                    return this.$main.Clock1$Timer();
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
                case 52:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 53:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 54:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 55:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 56:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 57:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 58:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 59:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 60:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 61:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 62:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 63:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 64:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 65:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 66:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 67:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 68:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 69:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 70:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 71:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 72:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 73:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 74:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 75:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 76:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 77:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 78:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 79:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 80:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 81:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 82:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 83:
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
        Lit12 = simpleSymbol;
        Lit199 = PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1660708);
        SimpleSymbol simpleSymbol2 = Lit216;
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("number").readResolve();
        Lit10 = simpleSymbol3;
        Lit195 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1660083), "/tmp/1584006425166_0.9763092664479837-0/youngandroidproject/../src/appinventor/ai_gumbraise/Pixal/Screen1.yail", 1660077);
        int[] iArr = new int[2];
        iArr[0] = -16777216;
        Lit172 = IntNum.make(iArr);
        int[] iArr2 = new int[2];
        iArr2[0] = -1;
        Lit168 = IntNum.make(iArr2);
        int[] iArr3 = new int[2];
        iArr3[0] = -16777216;
        Lit167 = IntNum.make(iArr3);
        int[] iArr4 = new int[2];
        iArr4[0] = -16777216;
        Lit163 = IntNum.make(iArr4);
        int[] iArr5 = new int[2];
        iArr5[0] = -1;
        Lit159 = IntNum.make(iArr5);
        int[] iArr6 = new int[2];
        iArr6[0] = -16777216;
        Lit158 = IntNum.make(iArr6);
        int[] iArr7 = new int[2];
        iArr7[0] = -16777216;
        Lit154 = IntNum.make(iArr7);
        int[] iArr8 = new int[2];
        iArr8[0] = -1;
        Lit150 = IntNum.make(iArr8);
        int[] iArr9 = new int[2];
        iArr9[0] = -16777216;
        Lit149 = IntNum.make(iArr9);
        int[] iArr10 = new int[2];
        iArr10[0] = -16777216;
        Lit145 = IntNum.make(iArr10);
        int[] iArr11 = new int[2];
        iArr11[0] = -1;
        Lit141 = IntNum.make(iArr11);
        int[] iArr12 = new int[2];
        iArr12[0] = -16777216;
        Lit140 = IntNum.make(iArr12);
        int[] iArr13 = new int[2];
        iArr13[0] = -16777216;
        Lit136 = IntNum.make(iArr13);
        int[] iArr14 = new int[2];
        iArr14[0] = -1;
        Lit132 = IntNum.make(iArr14);
        int[] iArr15 = new int[2];
        iArr15[0] = -16777216;
        Lit131 = IntNum.make(iArr15);
        int[] iArr16 = new int[2];
        iArr16[0] = -16777216;
        Lit127 = IntNum.make(iArr16);
        int[] iArr17 = new int[2];
        iArr17[0] = -1;
        Lit123 = IntNum.make(iArr17);
        int[] iArr18 = new int[2];
        iArr18[0] = -16777216;
        Lit119 = IntNum.make(iArr18);
        int[] iArr19 = new int[2];
        iArr19[0] = -1;
        Lit115 = IntNum.make(iArr19);
        int[] iArr20 = new int[2];
        iArr20[0] = -16777216;
        Lit111 = IntNum.make(iArr20);
        int[] iArr21 = new int[2];
        iArr21[0] = -1;
        Lit107 = IntNum.make(iArr21);
        int[] iArr22 = new int[2];
        iArr22[0] = -16777216;
        Lit105 = IntNum.make(iArr22);
        int[] iArr23 = new int[2];
        iArr23[0] = -16777216;
        Lit101 = IntNum.make(iArr23);
        int[] iArr24 = new int[2];
        iArr24[0] = -1;
        Lit97 = IntNum.make(iArr24);
        int[] iArr25 = new int[2];
        iArr25[0] = -16777216;
        Lit96 = IntNum.make(iArr25);
        int[] iArr26 = new int[2];
        iArr26[0] = -1;
        Lit90 = IntNum.make(iArr26);
        int[] iArr27 = new int[2];
        iArr27[0] = -16777216;
        Lit88 = IntNum.make(iArr27);
        int[] iArr28 = new int[2];
        iArr28[0] = -1;
        Lit78 = IntNum.make(iArr28);
        int[] iArr29 = new int[2];
        iArr29[0] = -16777216;
        Lit76 = IntNum.make(iArr29);
        int[] iArr30 = new int[2];
        iArr30[0] = -1;
        Lit58 = IntNum.make(iArr30);
        int[] iArr31 = new int[2];
        iArr31[0] = -16777216;
        Lit50 = IntNum.make(iArr31);
        int[] iArr32 = new int[2];
        iArr32[0] = -16777216;
        Lit35 = IntNum.make(iArr32);
        int[] iArr33 = new int[2];
        iArr33[0] = -16777216;
        Lit20 = IntNum.make(iArr33);
        int[] iArr34 = new int[2];
        iArr34[0] = -16777216;
        Lit18 = IntNum.make(iArr34);
        int[] iArr35 = new int[2];
        iArr35[0] = -16777216;
        Lit14 = IntNum.make(iArr35);
        int[] iArr36 = new int[2];
        iArr36[0] = -16777216;
        Lit9 = IntNum.make(iArr36);
    }

    public Screen1() {
        ModuleInfo.register(this);
        frame frame2 = new frame();
        this.get$Mnsimple$Mnname = new ModuleMethod(frame2, 1, Lit202, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.onCreate = new ModuleMethod(frame2, 2, "onCreate", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.android$Mnlog$Mnform = new ModuleMethod(frame2, 3, Lit203, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(frame2, 4, Lit204, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 5, Lit205, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 7, Lit206, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(frame2, 8, Lit207, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(frame2, 9, Lit208, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(frame2, 10, Lit209, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(frame2, 11, Lit210, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(frame2, 12, Lit211, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(frame2, 13, Lit212, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(frame2, 14, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(frame2, 15, Lit213, 16388);
        this.dispatchGenericEvent = new ModuleMethod(frame2, 16, Lit214, 16388);
        this.lookup$Mnhandler = new ModuleMethod(frame2, 17, Lit215, 8194);
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
        this.Screen1$Initialize = new ModuleMethod(frame2, 26, Lit31, 0);
        lambda$Fn8 = new ModuleMethod(frame2, 27, null, 0);
        lambda$Fn9 = new ModuleMethod(frame2, 28, null, 0);
        lambda$Fn10 = new ModuleMethod(frame2, 29, null, 0);
        lambda$Fn11 = new ModuleMethod(frame2, 30, null, 0);
        this.A2$Click = new ModuleMethod(frame2, 31, Lit46, 0);
        lambda$Fn12 = new ModuleMethod(frame2, 32, null, 0);
        lambda$Fn13 = new ModuleMethod(frame2, 33, null, 0);
        this.A3$Click = new ModuleMethod(frame2, 34, Lit61, 0);
        lambda$Fn14 = new ModuleMethod(frame2, 35, null, 0);
        lambda$Fn15 = new ModuleMethod(frame2, 36, null, 0);
        lambda$Fn16 = new ModuleMethod(frame2, 37, null, 0);
        lambda$Fn17 = new ModuleMethod(frame2, 38, null, 0);
        lambda$Fn18 = new ModuleMethod(frame2, 39, null, 0);
        lambda$Fn19 = new ModuleMethod(frame2, 40, null, 0);
        this.A6$Click = new ModuleMethod(frame2, 41, Lit81, 0);
        lambda$Fn20 = new ModuleMethod(frame2, 42, null, 0);
        lambda$Fn21 = new ModuleMethod(frame2, 43, null, 0);
        lambda$Fn22 = new ModuleMethod(frame2, 44, null, 0);
        lambda$Fn23 = new ModuleMethod(frame2, 45, null, 0);
        this.A10$Click = new ModuleMethod(frame2, 46, Lit93, 0);
        lambda$Fn24 = new ModuleMethod(frame2, 47, null, 0);
        lambda$Fn25 = new ModuleMethod(frame2, 48, null, 0);
        lambda$Fn26 = new ModuleMethod(frame2, 49, null, 0);
        lambda$Fn27 = new ModuleMethod(frame2, 50, null, 0);
        lambda$Fn28 = new ModuleMethod(frame2, 51, null, 0);
        lambda$Fn29 = new ModuleMethod(frame2, 52, null, 0);
        lambda$Fn30 = new ModuleMethod(frame2, 53, null, 0);
        lambda$Fn31 = new ModuleMethod(frame2, 54, null, 0);
        lambda$Fn32 = new ModuleMethod(frame2, 55, null, 0);
        lambda$Fn33 = new ModuleMethod(frame2, 56, null, 0);
        lambda$Fn34 = new ModuleMethod(frame2, 57, null, 0);
        lambda$Fn35 = new ModuleMethod(frame2, 58, null, 0);
        lambda$Fn36 = new ModuleMethod(frame2, 59, null, 0);
        lambda$Fn37 = new ModuleMethod(frame2, 60, null, 0);
        lambda$Fn38 = new ModuleMethod(frame2, 61, null, 0);
        lambda$Fn39 = new ModuleMethod(frame2, 62, null, 0);
        lambda$Fn40 = new ModuleMethod(frame2, 63, null, 0);
        lambda$Fn41 = new ModuleMethod(frame2, 64, null, 0);
        lambda$Fn42 = new ModuleMethod(frame2, 65, null, 0);
        lambda$Fn43 = new ModuleMethod(frame2, 66, null, 0);
        lambda$Fn44 = new ModuleMethod(frame2, 67, null, 0);
        lambda$Fn45 = new ModuleMethod(frame2, 68, null, 0);
        lambda$Fn46 = new ModuleMethod(frame2, 69, null, 0);
        lambda$Fn47 = new ModuleMethod(frame2, 70, null, 0);
        lambda$Fn48 = new ModuleMethod(frame2, 71, null, 0);
        lambda$Fn49 = new ModuleMethod(frame2, 72, null, 0);
        lambda$Fn50 = new ModuleMethod(frame2, 73, null, 0);
        lambda$Fn51 = new ModuleMethod(frame2, 74, null, 0);
        lambda$Fn52 = new ModuleMethod(frame2, 75, null, 0);
        lambda$Fn53 = new ModuleMethod(frame2, 76, null, 0);
        lambda$Fn54 = new ModuleMethod(frame2, 77, null, 0);
        lambda$Fn55 = new ModuleMethod(frame2, 78, null, 0);
        lambda$Fn56 = new ModuleMethod(frame2, 79, null, 0);
        lambda$Fn57 = new ModuleMethod(frame2, 80, null, 0);
        lambda$Fn58 = new ModuleMethod(frame2, 81, null, 0);
        lambda$Fn59 = new ModuleMethod(frame2, 82, null, 0);
        this.Clock1$Timer = new ModuleMethod(frame2, 83, Lit200, 0);
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
        Object find = require.find("com.google.youngandroid.runtime");
        try {
            ((Runnable) find).run();
            this.$Stdebug$Mnform$St = Boolean.FALSE;
            this.form$Mnenvironment = Environment.make(misc.symbol$To$String(Lit0));
            FString stringAppend = strings.stringAppend(misc.symbol$To$String(Lit0), "-global-vars");
            if (stringAppend == null) {
                obj = null;
            } else {
                obj = stringAppend.toString();
            }
            this.global$Mnvar$Mnenvironment = Environment.make(obj);
            Screen1 = null;
            this.form$Mnname$Mnsymbol = Lit0;
            this.events$Mnto$Mnregister = LList.Empty;
            this.components$Mnto$Mncreate = LList.Empty;
            this.global$Mnvars$Mnto$Mncreate = LList.Empty;
            this.form$Mndo$Mnafter$Mncreation = LList.Empty;
            Object find2 = require.find("com.google.youngandroid.runtime");
            try {
                ((Runnable) find2).run();
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
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit5, ""), $result);
                } else {
                    addToGlobalVars(Lit5, lambda$Fn4);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit6, ""), $result);
                } else {
                    addToGlobalVars(Lit6, lambda$Fn5);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit7, ""), $result);
                } else {
                    addToGlobalVars(Lit7, lambda$Fn6);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit8, Lit9, Lit10);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit11, "Pixal", Lit12);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit13, Lit14, Lit10);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit15, "56673328.png", Lit12);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit16, "zoom", Lit12);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit17, Lit18, Lit10);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit19, Lit20, Lit10);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit21, Boolean.TRUE, Lit22);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit23, Boolean.FALSE, Lit22);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit24, "Fixed", Lit12);
                    runtime.setAndCoerceProperty$Ex(Lit0, Lit25, "Screen1", Lit12);
                    Values.writeValues(runtime.setAndCoerceProperty$Ex(Lit0, Lit26, Boolean.FALSE, Lit22), $result);
                } else {
                    addToFormDoAfterCreation(new Promise(lambda$Fn7));
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit31, this.Screen1$Initialize);
                } else {
                    addToFormEnvironment(Lit31, this.Screen1$Initialize);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Screen1", "Initialize");
                } else {
                    addToEvents(Lit0, Lit32);
                }
                this.A1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit33, Lit34, lambda$Fn8), $result);
                } else {
                    addToComponents(Lit0, Lit38, Lit34, lambda$Fn9);
                }
                this.A2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit34, Lit39, Lit40, lambda$Fn10), $result);
                } else {
                    addToComponents(Lit34, Lit44, Lit40, lambda$Fn11);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit46, this.A2$Click);
                } else {
                    addToFormEnvironment(Lit46, this.A2$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "A2", "Click");
                } else {
                    addToEvents(Lit40, Lit47);
                }
                this.A3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit34, Lit48, Lit49, lambda$Fn12), $result);
                } else {
                    addToComponents(Lit34, Lit59, Lit49, lambda$Fn13);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit61, this.A3$Click);
                } else {
                    addToFormEnvironment(Lit61, this.A3$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "A3", "Click");
                } else {
                    addToEvents(Lit49, Lit47);
                }
                this.A4 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit62, Lit63, lambda$Fn14), $result);
                } else {
                    addToComponents(Lit0, Lit64, Lit63, lambda$Fn15);
                }
                this.A5 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit63, Lit65, Lit66, lambda$Fn16), $result);
                } else {
                    addToComponents(Lit63, Lit73, Lit66, lambda$Fn17);
                }
                this.A6 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit66, Lit74, Lit75, lambda$Fn18), $result);
                } else {
                    addToComponents(Lit66, Lit79, Lit75, lambda$Fn19);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit81, this.A6$Click);
                } else {
                    addToFormEnvironment(Lit81, this.A6$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "A6", "Click");
                } else {
                    addToEvents(Lit75, Lit47);
                }
                this.A9 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit63, Lit82, Lit83, lambda$Fn20), $result);
                } else {
                    addToComponents(Lit63, Lit85, Lit83, lambda$Fn21);
                }
                this.A10 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit83, Lit86, Lit87, lambda$Fn22), $result);
                } else {
                    addToComponents(Lit83, Lit91, Lit87, lambda$Fn23);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit93, this.A10$Click);
                } else {
                    addToFormEnvironment(Lit93, this.A10$Click);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "A10", "Click");
                } else {
                    addToEvents(Lit87, Lit47);
                }
                this.A15 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit94, Lit95, lambda$Fn24), $result);
                } else {
                    addToComponents(Lit0, Lit98, Lit95, lambda$Fn25);
                }
                this.HorizontalScrollArrangement2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit99, Lit100, lambda$Fn26), $result);
                } else {
                    addToComponents(Lit0, Lit102, Lit100, lambda$Fn27);
                }
                this.ValAccel = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit103, Lit104, lambda$Fn28), $result);
                } else {
                    addToComponents(Lit0, Lit108, Lit104, lambda$Fn29);
                }
                this.HorizontalScrollArrangement12 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit109, Lit110, lambda$Fn30), $result);
                } else {
                    addToComponents(Lit0, Lit112, Lit110, lambda$Fn31);
                }
                this.Label9 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit113, Lit114, lambda$Fn32), $result);
                } else {
                    addToComponents(Lit0, Lit116, Lit114, lambda$Fn33);
                }
                this.HorizontalScrollArrangement3 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit117, Lit118, lambda$Fn34), $result);
                } else {
                    addToComponents(Lit0, Lit120, Lit118, lambda$Fn35);
                }
                this.f0ValRgi = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit121, Lit122, lambda$Fn36), $result);
                } else {
                    addToComponents(Lit0, Lit124, Lit122, lambda$Fn37);
                }
                this.HorizontalScrollArrangement11 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit125, Lit126, lambda$Fn38), $result);
                } else {
                    addToComponents(Lit0, Lit128, Lit126, lambda$Fn39);
                }
                this.A17 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit129, Lit130, lambda$Fn40), $result);
                } else {
                    addToComponents(Lit0, Lit133, Lit130, lambda$Fn41);
                }
                this.HorizontalScrollArrangement10 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit134, Lit135, lambda$Fn42), $result);
                } else {
                    addToComponents(Lit0, Lit137, Lit135, lambda$Fn43);
                }
                this.ValTemp = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit138, Lit139, lambda$Fn44), $result);
                } else {
                    addToComponents(Lit0, Lit142, Lit139, lambda$Fn45);
                }
                this.HorizontalScrollArrangement5 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit143, Lit144, lambda$Fn46), $result);
                } else {
                    addToComponents(Lit0, Lit146, Lit144, lambda$Fn47);
                }
                this.Label8 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit144, Lit147, Lit148, lambda$Fn48), $result);
                } else {
                    addToComponents(Lit144, Lit151, Lit148, lambda$Fn49);
                }
                this.HorizontalScrollArrangement14 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit152, Lit153, lambda$Fn50), $result);
                } else {
                    addToComponents(Lit0, Lit155, Lit153, lambda$Fn51);
                }
                this.Label2 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit156, Lit157, lambda$Fn52), $result);
                } else {
                    addToComponents(Lit0, Lit160, Lit157, lambda$Fn53);
                }
                this.HorizontalArrangement1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit161, Lit162, lambda$Fn54), $result);
                } else {
                    addToComponents(Lit0, Lit164, Lit162, lambda$Fn55);
                }
                this.ValNive = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit165, Lit166, lambda$Fn56), $result);
                } else {
                    addToComponents(Lit0, Lit169, Lit166, lambda$Fn57);
                }
                this.HorizontalScrollArrangement13 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit170, Lit171, lambda$Fn58), $result);
                } else {
                    addToComponents(Lit0, Lit173, Lit171, lambda$Fn59);
                }
                this.BluetoothClient1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit174, Lit175, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit176, Lit175, Boolean.FALSE);
                }
                this.TinyDB1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit177, Lit178, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit179, Lit178, Boolean.FALSE);
                }
                this.Clock1 = null;
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit180, Lit27, Boolean.FALSE), $result);
                } else {
                    addToComponents(Lit0, Lit181, Lit27, Boolean.FALSE);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    runtime.addToCurrentFormEnvironment(Lit200, this.Clock1$Timer);
                } else {
                    addToFormEnvironment(Lit200, this.Clock1$Timer);
                }
                if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
                    EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Clock1", "Timer");
                } else {
                    addToEvents(Lit27, Lit201);
                }
                runtime.initRuntime();
            } catch (ClassCastException e) {
                throw new WrongType(e, "java.lang.Runnable.run()", 1, find2);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "java.lang.Runnable.run()", 1, find);
        }
    }

    static String lambda3() {
        return "";
    }

    static String lambda4() {
        return "";
    }

    static String lambda5() {
        return "";
    }

    static String lambda6() {
        return "";
    }

    static String lambda7() {
        return "";
    }

    static Object lambda8() {
        runtime.setAndCoerceProperty$Ex(Lit0, Lit8, Lit9, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit11, "Pixal", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit13, Lit14, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit15, "56673328.png", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit16, "zoom", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit17, Lit18, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit19, Lit20, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit21, Boolean.TRUE, Lit22);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit23, Boolean.FALSE, Lit22);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit24, "Fixed", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit25, "Screen1", Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit0, Lit26, Boolean.FALSE, Lit22);
    }

    public Object Screen1$Initialize() {
        runtime.setThisForm();
        runtime.setAndCoerceProperty$Ex(Lit27, Lit28, Boolean.TRUE, Lit22);
        return runtime.setAndCoerceProperty$Ex(Lit27, Lit29, Lit30, Lit10);
    }

    static Object lambda10() {
        runtime.setAndCoerceProperty$Ex(Lit34, Lit13, Lit35, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit34, Lit36, Lit37, Lit10);
    }

    static Object lambda9() {
        runtime.setAndCoerceProperty$Ex(Lit34, Lit13, Lit35, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit34, Lit36, Lit37, Lit10);
    }

    static Object lambda11() {
        runtime.setAndCoerceProperty$Ex(Lit40, Lit41, Lit42, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit40, Lit43, "56673328.png", Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit40, Lit36, Lit42, Lit10);
    }

    static Object lambda12() {
        runtime.setAndCoerceProperty$Ex(Lit40, Lit41, Lit42, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit40, Lit43, "56673328.png", Lit12);
        return runtime.setAndCoerceProperty$Ex(Lit40, Lit36, Lit42, Lit10);
    }

    public Object A2$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen5"), Lit45, "open another screen");
    }

    static Object lambda13() {
        runtime.setAndCoerceProperty$Ex(Lit49, Lit13, Lit50, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit51, Boolean.TRUE, Lit22);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit52, Lit53, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit54, "Compte", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit55, Lit56, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit57, Lit58, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit49, Lit36, Lit37, Lit10);
    }

    static Object lambda14() {
        runtime.setAndCoerceProperty$Ex(Lit49, Lit13, Lit50, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit51, Boolean.TRUE, Lit22);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit52, Lit53, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit54, "Compte", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit55, Lit56, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit49, Lit57, Lit58, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit49, Lit36, Lit37, Lit10);
    }

    public Object A3$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen3"), Lit60, "open another screen");
    }

    static Object lambda15() {
        return runtime.setAndCoerceProperty$Ex(Lit63, Lit36, Lit37, Lit10);
    }

    static Object lambda16() {
        return runtime.setAndCoerceProperty$Ex(Lit63, Lit36, Lit37, Lit10);
    }

    static Object lambda17() {
        runtime.setAndCoerceProperty$Ex(Lit66, Lit67, Lit56, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit66, Lit68, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit66, Lit70, Lit71, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit66, Lit36, Lit72, Lit10);
    }

    static Object lambda18() {
        runtime.setAndCoerceProperty$Ex(Lit66, Lit67, Lit56, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit66, Lit68, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit66, Lit70, Lit71, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit66, Lit36, Lit72, Lit10);
    }

    static Object lambda19() {
        runtime.setAndCoerceProperty$Ex(Lit75, Lit13, Lit76, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit75, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit75, Lit54, "Ajouter VRC", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit75, Lit57, Lit78, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit75, Lit36, Lit37, Lit10);
    }

    static Object lambda20() {
        runtime.setAndCoerceProperty$Ex(Lit75, Lit13, Lit76, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit75, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit75, Lit54, "Ajouter VRC", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit75, Lit57, Lit78, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit75, Lit36, Lit37, Lit10);
    }

    public Object A6$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen6"), Lit80, "open another screen");
    }

    static Object lambda21() {
        runtime.setAndCoerceProperty$Ex(Lit83, Lit68, Lit71, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit83, Lit70, Lit71, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit83, Lit36, Lit84, Lit10);
    }

    static Object lambda22() {
        runtime.setAndCoerceProperty$Ex(Lit83, Lit68, Lit71, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit83, Lit70, Lit71, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit83, Lit36, Lit84, Lit10);
    }

    static Object lambda23() {
        runtime.setAndCoerceProperty$Ex(Lit87, Lit13, Lit88, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit87, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit87, Lit41, Lit37, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit87, Lit89, Lit56, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit87, Lit54, "Bluetooth", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit87, Lit57, Lit90, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit87, Lit36, Lit37, Lit10);
    }

    static Object lambda24() {
        runtime.setAndCoerceProperty$Ex(Lit87, Lit13, Lit88, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit87, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit87, Lit41, Lit37, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit87, Lit89, Lit56, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit87, Lit54, "Bluetooth", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit87, Lit57, Lit90, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit87, Lit36, Lit37, Lit10);
    }

    public Object A10$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen2"), Lit92, "open another screen");
    }

    static Object lambda25() {
        runtime.setAndCoerceProperty$Ex(Lit95, Lit13, Lit96, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit95, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit95, Lit54, "Accélération :", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit95, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit95, Lit57, Lit97, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit95, Lit36, Lit37, Lit10);
    }

    static Object lambda26() {
        runtime.setAndCoerceProperty$Ex(Lit95, Lit13, Lit96, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit95, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit95, Lit54, "Accélération :", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit95, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit95, Lit57, Lit97, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit95, Lit36, Lit37, Lit10);
    }

    static Object lambda27() {
        runtime.setAndCoerceProperty$Ex(Lit100, Lit13, Lit101, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit100, Lit41, Lit42, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit100, Lit36, Lit37, Lit10);
    }

    static Object lambda28() {
        runtime.setAndCoerceProperty$Ex(Lit100, Lit13, Lit101, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit100, Lit41, Lit42, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit100, Lit36, Lit37, Lit10);
    }

    static Object lambda29() {
        runtime.setAndCoerceProperty$Ex(Lit104, Lit13, Lit105, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit104, Lit52, Lit106, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit104, Lit54, "0", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit104, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit104, Lit57, Lit107, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit104, Lit36, Lit37, Lit10);
    }

    static Object lambda30() {
        runtime.setAndCoerceProperty$Ex(Lit104, Lit13, Lit105, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit104, Lit52, Lit106, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit104, Lit54, "0", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit104, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit104, Lit57, Lit107, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit104, Lit36, Lit37, Lit10);
    }

    static Object lambda31() {
        runtime.setAndCoerceProperty$Ex(Lit110, Lit13, Lit111, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit110, Lit41, Lit42, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit110, Lit36, Lit37, Lit10);
    }

    static Object lambda32() {
        runtime.setAndCoerceProperty$Ex(Lit110, Lit13, Lit111, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit110, Lit41, Lit42, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit110, Lit36, Lit37, Lit10);
    }

    static Object lambda33() {
        runtime.setAndCoerceProperty$Ex(Lit114, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit114, Lit54, "Régime moteur :", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit114, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit114, Lit57, Lit115, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit114, Lit36, Lit37, Lit10);
    }

    static Object lambda34() {
        runtime.setAndCoerceProperty$Ex(Lit114, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit114, Lit54, "Régime moteur :", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit114, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit114, Lit57, Lit115, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit114, Lit36, Lit37, Lit10);
    }

    static Object lambda35() {
        runtime.setAndCoerceProperty$Ex(Lit118, Lit13, Lit119, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit118, Lit41, Lit42, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit118, Lit36, Lit37, Lit10);
    }

    static Object lambda36() {
        runtime.setAndCoerceProperty$Ex(Lit118, Lit13, Lit119, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit118, Lit41, Lit42, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit118, Lit36, Lit37, Lit10);
    }

    static Object lambda37() {
        runtime.setAndCoerceProperty$Ex(Lit122, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit122, Lit54, "0", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit122, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit122, Lit57, Lit123, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit122, Lit36, Lit37, Lit10);
    }

    static Object lambda38() {
        runtime.setAndCoerceProperty$Ex(Lit122, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit122, Lit54, "0", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit122, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit122, Lit57, Lit123, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit122, Lit36, Lit37, Lit10);
    }

    static Object lambda39() {
        runtime.setAndCoerceProperty$Ex(Lit126, Lit13, Lit127, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit126, Lit41, Lit42, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit126, Lit36, Lit37, Lit10);
    }

    static Object lambda40() {
        runtime.setAndCoerceProperty$Ex(Lit126, Lit13, Lit127, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit126, Lit41, Lit42, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit126, Lit36, Lit37, Lit10);
    }

    static Object lambda41() {
        runtime.setAndCoerceProperty$Ex(Lit130, Lit13, Lit131, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit130, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit130, Lit54, "Température moteur :", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit130, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit130, Lit57, Lit132, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit130, Lit36, Lit37, Lit10);
    }

    static Object lambda42() {
        runtime.setAndCoerceProperty$Ex(Lit130, Lit13, Lit131, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit130, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit130, Lit54, "Température moteur :", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit130, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit130, Lit57, Lit132, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit130, Lit36, Lit37, Lit10);
    }

    static Object lambda43() {
        runtime.setAndCoerceProperty$Ex(Lit135, Lit13, Lit136, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit41, Lit42, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit135, Lit36, Lit37, Lit10);
    }

    static Object lambda44() {
        runtime.setAndCoerceProperty$Ex(Lit135, Lit13, Lit136, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit41, Lit42, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit135, Lit36, Lit37, Lit10);
    }

    static Object lambda45() {
        runtime.setAndCoerceProperty$Ex(Lit139, Lit13, Lit140, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit139, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit139, Lit54, "0", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit139, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit139, Lit57, Lit141, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit139, Lit36, Lit37, Lit10);
    }

    static Object lambda46() {
        runtime.setAndCoerceProperty$Ex(Lit139, Lit13, Lit140, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit139, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit139, Lit54, "0", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit139, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit139, Lit57, Lit141, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit139, Lit36, Lit37, Lit10);
    }

    static Object lambda47() {
        runtime.setAndCoerceProperty$Ex(Lit144, Lit13, Lit145, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit144, Lit36, Lit37, Lit10);
    }

    static Object lambda48() {
        runtime.setAndCoerceProperty$Ex(Lit144, Lit13, Lit145, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit144, Lit36, Lit37, Lit10);
    }

    static Object lambda49() {
        runtime.setAndCoerceProperty$Ex(Lit148, Lit13, Lit149, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit148, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit148, Lit54, "°C", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit148, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit148, Lit57, Lit150, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit148, Lit36, Lit37, Lit10);
    }

    static Object lambda50() {
        runtime.setAndCoerceProperty$Ex(Lit148, Lit13, Lit149, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit148, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit148, Lit54, "°C", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit148, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit148, Lit57, Lit150, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit148, Lit36, Lit37, Lit10);
    }

    static Object lambda51() {
        runtime.setAndCoerceProperty$Ex(Lit153, Lit13, Lit154, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit153, Lit41, Lit42, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit153, Lit36, Lit37, Lit10);
    }

    static Object lambda52() {
        runtime.setAndCoerceProperty$Ex(Lit153, Lit13, Lit154, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit153, Lit41, Lit42, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit153, Lit36, Lit37, Lit10);
    }

    static Object lambda53() {
        runtime.setAndCoerceProperty$Ex(Lit157, Lit13, Lit158, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit157, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit157, Lit54, "Niveau de batterie :", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit157, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit157, Lit57, Lit159, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit157, Lit36, Lit37, Lit10);
    }

    static Object lambda54() {
        runtime.setAndCoerceProperty$Ex(Lit157, Lit13, Lit158, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit157, Lit52, Lit77, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit157, Lit54, "Niveau de batterie :", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit157, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit157, Lit57, Lit159, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit157, Lit36, Lit37, Lit10);
    }

    static Object lambda55() {
        runtime.setAndCoerceProperty$Ex(Lit162, Lit13, Lit163, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit162, Lit41, Lit42, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit162, Lit36, Lit37, Lit10);
    }

    static Object lambda56() {
        runtime.setAndCoerceProperty$Ex(Lit162, Lit13, Lit163, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit162, Lit41, Lit42, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit162, Lit36, Lit37, Lit10);
    }

    static Object lambda57() {
        runtime.setAndCoerceProperty$Ex(Lit166, Lit13, Lit167, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit166, Lit52, Lit106, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit166, Lit54, "0", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit166, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit166, Lit57, Lit168, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit166, Lit36, Lit37, Lit10);
    }

    static Object lambda58() {
        runtime.setAndCoerceProperty$Ex(Lit166, Lit13, Lit167, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit166, Lit52, Lit106, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit166, Lit54, "0", Lit12);
        runtime.setAndCoerceProperty$Ex(Lit166, Lit55, Lit69, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit166, Lit57, Lit168, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit166, Lit36, Lit37, Lit10);
    }

    static Object lambda59() {
        runtime.setAndCoerceProperty$Ex(Lit171, Lit13, Lit172, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit171, Lit41, Lit42, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit171, Lit36, Lit37, Lit10);
    }

    static Object lambda60() {
        runtime.setAndCoerceProperty$Ex(Lit171, Lit13, Lit172, Lit10);
        runtime.setAndCoerceProperty$Ex(Lit171, Lit41, Lit42, Lit10);
        return runtime.setAndCoerceProperty$Ex(Lit171, Lit36, Lit37, Lit10);
    }

    public Object Clock1$Timer() {
        runtime.setThisForm();
        if (runtime.callYailPrimitive(Scheme.numGrt, LList.list2(runtime.callComponentMethod(Lit175, Lit182, LList.Empty, LList.Empty), Lit71), Lit183, ">") == Boolean.FALSE) {
            return Values.empty;
        }
        runtime.addGlobalVarToCurrentFormEnvironment(Lit3, runtime.callComponentMethod(Lit175, Lit184, LList.list1(runtime.callComponentMethod(Lit175, Lit182, LList.Empty, LList.Empty)), Lit185));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit4, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.string$Mnsplit, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St), ","), Lit186, "split"), Lit69), Lit187, "select list item"));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit5, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.string$Mnsplit, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St), ","), Lit188, "split"), Lit56), Lit189, "select list item"));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit6, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.string$Mnsplit, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St), ","), Lit190, "split"), Lit191), Lit192, "select list item"));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit7, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.string$Mnsplit, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St), ","), Lit193, "split"), Lit194), Lit195, "select list item"));
        if (runtime.callYailPrimitive(runtime.is$Mnnumber$Qu, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit196, "is a number?") != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit104, Lit54, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit4, runtime.$Stthe$Mnnull$Mnvalue$St), Lit12);
        }
        if (runtime.callYailPrimitive(runtime.is$Mnnumber$Qu, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit5, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit197, "is a number?") != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit122, Lit54, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit5, runtime.$Stthe$Mnnull$Mnvalue$St), Lit12);
        }
        if (runtime.callYailPrimitive(runtime.is$Mnnumber$Qu, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit6, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit198, "is a number?") != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit139, Lit54, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit6, runtime.$Stthe$Mnnull$Mnvalue$St), Lit12);
        }
        return runtime.callYailPrimitive(runtime.is$Mnnumber$Qu, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit7, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit199, "is a number?") != Boolean.FALSE ? runtime.setAndCoerceProperty$Ex(Lit166, Lit54, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit7, runtime.$Stthe$Mnnull$Mnvalue$St), Lit12) : Values.empty;
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
        Screen1 = this;
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
