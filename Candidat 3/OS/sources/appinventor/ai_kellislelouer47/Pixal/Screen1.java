package appinventor.ai_kellislelouer47.Pixal;

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

/* compiled from: Screen1.yail */
public class Screen1 extends Form implements Runnable {
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("Screen1").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("getMessage").readResolve());
    static final PairWithPosition Lit10 = PairWithPosition.make(Lit21, PairWithPosition.make(Lit252, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 36976), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 36970);
    static final SimpleSymbol Lit100 = ((SimpleSymbol) new SimpleSymbol("FontTypeface").readResolve());
    static final IntNum Lit101;
    static final FString Lit102 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit103 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final IntNum Lit104 = IntNum.make(16777215);
    static final FString Lit105 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit106 = new FString("com.google.appinventor.components.runtime.TableArrangement");
    static final SimpleSymbol Lit107 = ((SimpleSymbol) new SimpleSymbol("A4").readResolve());
    static final FString Lit108 = new FString("com.google.appinventor.components.runtime.TableArrangement");
    static final FString Lit109 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final PairWithPosition Lit11 = PairWithPosition.make(Lit21, PairWithPosition.make(Lit252, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 36976), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 36970);
    static final SimpleSymbol Lit110 = ((SimpleSymbol) new SimpleSymbol("A5").readResolve());
    static final SimpleSymbol Lit111 = ((SimpleSymbol) new SimpleSymbol("Column").readResolve());
    static final SimpleSymbol Lit112 = ((SimpleSymbol) new SimpleSymbol("Row").readResolve());
    static final IntNum Lit113 = IntNum.make(-1050);
    static final FString Lit114 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit115 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit116 = ((SimpleSymbol) new SimpleSymbol("A6").readResolve());
    static final IntNum Lit117;
    static final IntNum Lit118 = IntNum.make(20);
    static final IntNum Lit119;
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("g$Trame").readResolve());
    static final FString Lit120 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit121 = PairWithPosition.make(Lit21, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 712782);
    static final SimpleSymbol Lit122 = ((SimpleSymbol) new SimpleSymbol("A6$Click").readResolve());
    static final FString Lit123 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit124 = ((SimpleSymbol) new SimpleSymbol("A9").readResolve());
    static final IntNum Lit125 = IntNum.make(-1050);
    static final FString Lit126 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit127 = new FString("com.google.appinventor.components.runtime.Button");
    static final IntNum Lit128;
    static final SimpleSymbol Lit129 = ((SimpleSymbol) new SimpleSymbol("Shape").readResolve());
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("g$Accel").readResolve());
    static final IntNum Lit130;
    static final FString Lit131 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit132 = PairWithPosition.make(Lit21, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 852046);
    static final SimpleSymbol Lit133 = ((SimpleSymbol) new SimpleSymbol("A10$Click").readResolve());
    static final FString Lit134 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit135 = ((SimpleSymbol) new SimpleSymbol("A15").readResolve());
    static final IntNum Lit136;
    static final IntNum Lit137;
    static final FString Lit138 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit139 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("g$Régi").readResolve());
    static final SimpleSymbol Lit140 = ((SimpleSymbol) new SimpleSymbol("HorizontalScrollArrangement12").readResolve());
    static final IntNum Lit141;
    static final FString Lit142 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit143 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit144 = ((SimpleSymbol) new SimpleSymbol("ValAccel").readResolve());
    static final IntNum Lit145;
    static final IntNum Lit146 = IntNum.make(18);
    static final IntNum Lit147;
    static final FString Lit148 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit149 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("g$Temp").readResolve());
    static final SimpleSymbol Lit150 = ((SimpleSymbol) new SimpleSymbol("HorizontalScrollArrangement3").readResolve());
    static final IntNum Lit151;
    static final FString Lit152 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit153 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit154 = ((SimpleSymbol) new SimpleSymbol("Label9").readResolve());
    static final IntNum Lit155;
    static final FString Lit156 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit157 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit158 = ((SimpleSymbol) new SimpleSymbol("HorizontalScrollArrangement11").readResolve());
    static final IntNum Lit159;
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("g$Nive").readResolve());
    static final FString Lit160 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit161 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit162 = ((SimpleSymbol) new SimpleSymbol("ValRégi").readResolve());
    static final IntNum Lit163;
    static final FString Lit164 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit165 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit166 = ((SimpleSymbol) new SimpleSymbol("HorizontalScrollArrangement10").readResolve());
    static final IntNum Lit167;
    static final FString Lit168 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit169 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("AccentColor").readResolve());
    static final SimpleSymbol Lit170 = ((SimpleSymbol) new SimpleSymbol("A17").readResolve());
    static final IntNum Lit171;
    static final IntNum Lit172;
    static final FString Lit173 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit174 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit175 = ((SimpleSymbol) new SimpleSymbol("HorizontalScrollArrangement5").readResolve());
    static final IntNum Lit176;
    static final FString Lit177 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit178 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit179 = ((SimpleSymbol) new SimpleSymbol("ValTemp").readResolve());
    static final IntNum Lit18;
    static final IntNum Lit180;
    static final IntNum Lit181;
    static final FString Lit182 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit183 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit184 = ((SimpleSymbol) new SimpleSymbol("Label8").readResolve());
    static final IntNum Lit185;
    static final IntNum Lit186;
    static final FString Lit187 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit188 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit189 = ((SimpleSymbol) new SimpleSymbol("HorizontalScrollArrangement14").readResolve());
    static final SimpleSymbol Lit19;
    static final IntNum Lit190;
    static final FString Lit191 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit192 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit193 = ((SimpleSymbol) new SimpleSymbol("Label2").readResolve());
    static final IntNum Lit194;
    static final IntNum Lit195;
    static final FString Lit196 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit197 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit198 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement1").readResolve());
    static final IntNum Lit199;
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("*the-null-value*").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("AppName").readResolve());
    static final FString Lit200 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit201 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit202 = ((SimpleSymbol) new SimpleSymbol("ValNive").readResolve());
    static final IntNum Lit203;
    static final IntNum Lit204;
    static final FString Lit205 = new FString("com.google.appinventor.components.runtime.Label");
    static final FString Lit206 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit207 = ((SimpleSymbol) new SimpleSymbol("HorizontalScrollArrangement13").readResolve());
    static final IntNum Lit208;
    static final FString Lit209 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit21;
    static final FString Lit210 = new FString("com.google.appinventor.components.runtime.BluetoothClient");
    static final FString Lit211 = new FString("com.google.appinventor.components.runtime.BluetoothClient");
    static final FString Lit212 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final FString Lit213 = new FString("com.google.appinventor.components.runtime.TinyDB");
    static final FString Lit214 = new FString("com.google.appinventor.components.runtime.Clock");
    static final FString Lit215 = new FString("com.google.appinventor.components.runtime.Clock");
    static final PairWithPosition Lit216 = PairWithPosition.make(Lit21, PairWithPosition.make(Lit252, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1884309), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1884303);
    static final PairWithPosition Lit217 = PairWithPosition.make(Lit252, PairWithPosition.make(Lit252, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1884324), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1884319);
    static final SimpleSymbol Lit218 = ((SimpleSymbol) new SimpleSymbol("BytesAvailableToReceive").readResolve());
    static final PairWithPosition Lit219 = PairWithPosition.make(Lit19, PairWithPosition.make(Lit19, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1884494), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1884486);
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("BackgroundColor").readResolve());
    static final SimpleSymbol Lit220 = ((SimpleSymbol) new SimpleSymbol("ReceiveText").readResolve());
    static final PairWithPosition Lit221 = PairWithPosition.make(Lit19, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1884702);
    static final PairWithPosition Lit222 = PairWithPosition.make(Lit21, PairWithPosition.make(Lit21, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1884874), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1884868);
    static final PairWithPosition Lit223 = PairWithPosition.make(Lit251, PairWithPosition.make(Lit19, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1884899), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1884893);
    static final PairWithPosition Lit224 = PairWithPosition.make(Lit21, PairWithPosition.make(Lit21, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1885088), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1885082);
    static final PairWithPosition Lit225 = PairWithPosition.make(Lit251, PairWithPosition.make(Lit19, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1885113), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1885107);
    static final PairWithPosition Lit226 = PairWithPosition.make(Lit21, PairWithPosition.make(Lit21, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1885302), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1885296);
    static final PairWithPosition Lit227 = PairWithPosition.make(Lit251, PairWithPosition.make(Lit19, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1885327), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1885321);
    static final PairWithPosition Lit228 = PairWithPosition.make(Lit21, PairWithPosition.make(Lit21, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1885516), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1885510);
    static final IntNum Lit229 = IntNum.make(4);
    static final IntNum Lit23;
    static final PairWithPosition Lit230;
    static final PairWithPosition Lit231 = PairWithPosition.make(Lit21, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1885645);
    static final PairWithPosition Lit232 = PairWithPosition.make(Lit21, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1885820);
    static final PairWithPosition Lit233 = PairWithPosition.make(Lit21, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1885993);
    static final PairWithPosition Lit234;
    static final SimpleSymbol Lit235 = ((SimpleSymbol) new SimpleSymbol("Clock1$Timer").readResolve());
    static final SimpleSymbol Lit236 = ((SimpleSymbol) new SimpleSymbol("Timer").readResolve());
    static final SimpleSymbol Lit237 = ((SimpleSymbol) new SimpleSymbol("get-simple-name").readResolve());
    static final SimpleSymbol Lit238 = ((SimpleSymbol) new SimpleSymbol("android-log-form").readResolve());
    static final SimpleSymbol Lit239 = ((SimpleSymbol) new SimpleSymbol("add-to-form-environment").readResolve());
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("Icon").readResolve());
    static final SimpleSymbol Lit240 = ((SimpleSymbol) new SimpleSymbol("lookup-in-form-environment").readResolve());
    static final SimpleSymbol Lit241 = ((SimpleSymbol) new SimpleSymbol("is-bound-in-form-environment").readResolve());
    static final SimpleSymbol Lit242 = ((SimpleSymbol) new SimpleSymbol("add-to-global-var-environment").readResolve());
    static final SimpleSymbol Lit243 = ((SimpleSymbol) new SimpleSymbol("add-to-events").readResolve());
    static final SimpleSymbol Lit244 = ((SimpleSymbol) new SimpleSymbol("add-to-components").readResolve());
    static final SimpleSymbol Lit245 = ((SimpleSymbol) new SimpleSymbol("add-to-global-vars").readResolve());
    static final SimpleSymbol Lit246 = ((SimpleSymbol) new SimpleSymbol("add-to-form-do-after-creation").readResolve());
    static final SimpleSymbol Lit247 = ((SimpleSymbol) new SimpleSymbol("send-error").readResolve());
    static final SimpleSymbol Lit248 = ((SimpleSymbol) new SimpleSymbol("dispatchEvent").readResolve());
    static final SimpleSymbol Lit249 = ((SimpleSymbol) new SimpleSymbol("dispatchGenericEvent").readResolve());
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("OpenScreenAnimation").readResolve());
    static final SimpleSymbol Lit250 = ((SimpleSymbol) new SimpleSymbol("lookup-handler").readResolve());
    static final SimpleSymbol Lit251 = ((SimpleSymbol) new SimpleSymbol("list").readResolve());
    static final SimpleSymbol Lit252 = ((SimpleSymbol) new SimpleSymbol("any").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("PrimaryColor").readResolve());
    static final IntNum Lit27;
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("PrimaryColorDark").readResolve());
    static final IntNum Lit29;
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("g$isConnected").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("Scrollable").readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN).readResolve());
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol("ShowListsAsJson").readResolve());
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol("Sizing").readResolve());
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("Title").readResolve());
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("TitleVisible").readResolve());
    static final PairWithPosition Lit36 = PairWithPosition.make(Lit21, PairWithPosition.make(Lit252, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 131214), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 131208);
    static final IntNum Lit37 = IntNum.make(1);
    static final PairWithPosition Lit38 = PairWithPosition.make(Lit252, PairWithPosition.make(Lit252, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 131229), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 131224);
    static final PairWithPosition Lit39 = PairWithPosition.make(Lit252, PairWithPosition.make(Lit252, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 131338), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 131333);
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol(TinyDB.DEFAULT_NAMESPACE).readResolve());
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement1").readResolve());
    static final SimpleSymbol Lit41 = ((SimpleSymbol) new SimpleSymbol("Visible").readResolve());
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("HorizontalArrangement2").readResolve());
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("Clock1").readResolve());
    static final SimpleSymbol Lit44 = ((SimpleSymbol) new SimpleSymbol("TimerEnabled").readResolve());
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("TimerInterval").readResolve());
    static final IntNum Lit46 = IntNum.make(500);
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("A3").readResolve());
    static final SimpleSymbol Lit48 = ((SimpleSymbol) new SimpleSymbol("Text").readResolve());
    static final PairWithPosition Lit49 = PairWithPosition.make(Lit21, PairWithPosition.make(Lit252, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 131813), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 131807);
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("GetValue").readResolve());
    static final PairWithPosition Lit50 = PairWithPosition.make(Lit252, PairWithPosition.make(Lit252, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 131828), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 131823);
    static final SimpleSymbol Lit51 = ((SimpleSymbol) new SimpleSymbol("BluetoothClient1").readResolve());
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("Connect").readResolve());
    static final PairWithPosition Lit53 = PairWithPosition.make(Lit21, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 131949);
    static final SimpleSymbol Lit54 = ((SimpleSymbol) new SimpleSymbol("A10").readResolve());
    static final PairWithPosition Lit55 = PairWithPosition.make(Lit21, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 132341);
    static final SimpleSymbol Lit56 = ((SimpleSymbol) new SimpleSymbol("Screen1$Initialize").readResolve());
    static final SimpleSymbol Lit57 = ((SimpleSymbol) new SimpleSymbol("Initialize").readResolve());
    static final FString Lit58 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final SimpleSymbol Lit59 = ((SimpleSymbol) new SimpleSymbol("A1").readResolve());
    static final IntNum Lit6 = IntNum.make(0);
    static final IntNum Lit60;
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("Width").readResolve());
    static final IntNum Lit62 = IntNum.make(-2);
    static final FString Lit63 = new FString("com.google.appinventor.components.runtime.HorizontalScrollArrangement");
    static final FString Lit64 = new FString("com.google.appinventor.components.runtime.Button");
    static final SimpleSymbol Lit65 = ((SimpleSymbol) new SimpleSymbol("A2").readResolve());
    static final SimpleSymbol Lit66 = ((SimpleSymbol) new SimpleSymbol("Height").readResolve());
    static final IntNum Lit67 = IntNum.make(50);
    static final SimpleSymbol Lit68 = ((SimpleSymbol) new SimpleSymbol("Image").readResolve());
    static final FString Lit69 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit7 = PairWithPosition.make(Lit21, PairWithPosition.make(Lit252, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 32875), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 32869);
    static final PairWithPosition Lit70 = PairWithPosition.make(Lit21, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 221262);
    static final SimpleSymbol Lit71 = ((SimpleSymbol) new SimpleSymbol("A2$Click").readResolve());
    static final SimpleSymbol Lit72 = ((SimpleSymbol) new SimpleSymbol("Click").readResolve());
    static final FString Lit73 = new FString("com.google.appinventor.components.runtime.Button");
    static final IntNum Lit74;
    static final SimpleSymbol Lit75 = ((SimpleSymbol) new SimpleSymbol("FontBold").readResolve());
    static final SimpleSymbol Lit76 = ((SimpleSymbol) new SimpleSymbol("FontSize").readResolve());
    static final IntNum Lit77 = IntNum.make(25);
    static final SimpleSymbol Lit78 = ((SimpleSymbol) new SimpleSymbol("TextAlignment").readResolve());
    static final IntNum Lit79 = IntNum.make(2);
    static final PairWithPosition Lit8 = PairWithPosition.make(Lit21, PairWithPosition.make(Lit252, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 32875), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 32869);
    static final SimpleSymbol Lit80 = ((SimpleSymbol) new SimpleSymbol("TextColor").readResolve());
    static final IntNum Lit81;
    static final FString Lit82 = new FString("com.google.appinventor.components.runtime.Button");
    static final PairWithPosition Lit83 = PairWithPosition.make(Lit252, PairWithPosition.make(Lit252, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 307295), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 307290);
    static final PairWithPosition Lit84 = PairWithPosition.make(Lit21, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 307387);
    static final PairWithPosition Lit85 = PairWithPosition.make(Lit21, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 307499);
    static final SimpleSymbol Lit86 = ((SimpleSymbol) new SimpleSymbol("A3$Click").readResolve());
    static final FString Lit87 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final SimpleSymbol Lit88 = ((SimpleSymbol) new SimpleSymbol("AlignHorizontal").readResolve());
    static final IntNum Lit89 = IntNum.make(3);
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("g$bluetoothData").readResolve());
    static final SimpleSymbol Lit90 = ((SimpleSymbol) new SimpleSymbol("AlignVertical").readResolve());
    static final IntNum Lit91 = IntNum.make(16777215);
    static final FString Lit92 = new FString("com.google.appinventor.components.runtime.HorizontalArrangement");
    static final FString Lit93 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final SimpleSymbol Lit94 = ((SimpleSymbol) new SimpleSymbol("VerticalArrangement2").readResolve());
    static final IntNum Lit95 = IntNum.make(16777215);
    static final FString Lit96 = new FString("com.google.appinventor.components.runtime.VerticalArrangement");
    static final FString Lit97 = new FString("com.google.appinventor.components.runtime.Label");
    static final SimpleSymbol Lit98 = ((SimpleSymbol) new SimpleSymbol("Label10").readResolve());
    static final IntNum Lit99 = IntNum.make(24);
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
    static final ModuleMethod lambda$Fn60 = null;
    static final ModuleMethod lambda$Fn61 = null;
    static final ModuleMethod lambda$Fn62 = null;
    static final ModuleMethod lambda$Fn63 = null;
    static final ModuleMethod lambda$Fn64 = null;
    static final ModuleMethod lambda$Fn65 = null;
    static final ModuleMethod lambda$Fn66 = null;
    static final ModuleMethod lambda$Fn67 = null;
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
    public HorizontalArrangement HorizontalArrangement2;
    public HorizontalScrollArrangement HorizontalScrollArrangement10;
    public HorizontalScrollArrangement HorizontalScrollArrangement11;
    public HorizontalScrollArrangement HorizontalScrollArrangement12;
    public HorizontalScrollArrangement HorizontalScrollArrangement13;
    public HorizontalScrollArrangement HorizontalScrollArrangement14;
    public HorizontalScrollArrangement HorizontalScrollArrangement3;
    public HorizontalScrollArrangement HorizontalScrollArrangement5;
    public Label Label10;
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
    public VerticalArrangement VerticalArrangement1;
    public VerticalArrangement VerticalArrangement2;
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
                    return Screen1.lambda9();
                case 27:
                    return Screen1.lambda10();
                case 28:
                    return this.$main.Screen1$Initialize();
                case 29:
                    return Screen1.lambda11();
                case 30:
                    return Screen1.lambda12();
                case 31:
                    return Screen1.lambda13();
                case 32:
                    return Screen1.lambda14();
                case 33:
                    return this.$main.A2$Click();
                case 34:
                    return Screen1.lambda15();
                case 35:
                    return Screen1.lambda16();
                case 36:
                    return this.$main.A3$Click();
                case 37:
                    return Screen1.lambda17();
                case 38:
                    return Screen1.lambda18();
                case 39:
                    return Screen1.lambda19();
                case 40:
                    return Screen1.lambda20();
                case 41:
                    return Screen1.lambda21();
                case 42:
                    return Screen1.lambda22();
                case 43:
                    return Screen1.lambda23();
                case 44:
                    return Screen1.lambda24();
                case 45:
                    return Screen1.lambda25();
                case 46:
                    return Screen1.lambda26();
                case 47:
                    return Screen1.lambda27();
                case 48:
                    return Screen1.lambda28();
                case 49:
                    return Screen1.lambda29();
                case 50:
                    return Screen1.lambda30();
                case 51:
                    return this.$main.A6$Click();
                case 52:
                    return Screen1.lambda31();
                case 53:
                    return Screen1.lambda32();
                case 54:
                    return Screen1.lambda33();
                case 55:
                    return Screen1.lambda34();
                case 56:
                    return this.$main.A10$Click();
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
                    return Screen1.lambda61();
                case 84:
                    return Screen1.lambda62();
                case 85:
                    return Screen1.lambda63();
                case 86:
                    return Screen1.lambda64();
                case 87:
                    return Screen1.lambda65();
                case 88:
                    return Screen1.lambda66();
                case 89:
                    return Screen1.lambda67();
                case 90:
                    return Screen1.lambda68();
                case 91:
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
                case 84:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 85:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 86:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 87:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 88:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 89:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 90:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 91:
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
        Lit21 = simpleSymbol;
        Lit234 = PairWithPosition.make(simpleSymbol, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1886166);
        SimpleSymbol simpleSymbol2 = Lit251;
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("number").readResolve();
        Lit19 = simpleSymbol3;
        Lit230 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol3, LList.Empty, "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1885541), "/tmp/1586899020441_0.7016905932686492-0/youngandroidproject/../src/appinventor/ai_kellislelouer47/Pixal/Screen1.yail", 1885535);
        int[] iArr = new int[2];
        iArr[0] = -16777216;
        Lit208 = IntNum.make(iArr);
        int[] iArr2 = new int[2];
        iArr2[0] = -1;
        Lit204 = IntNum.make(iArr2);
        int[] iArr3 = new int[2];
        iArr3[0] = -16777216;
        Lit203 = IntNum.make(iArr3);
        int[] iArr4 = new int[2];
        iArr4[0] = -16777216;
        Lit199 = IntNum.make(iArr4);
        int[] iArr5 = new int[2];
        iArr5[0] = -1;
        Lit195 = IntNum.make(iArr5);
        int[] iArr6 = new int[2];
        iArr6[0] = -16777216;
        Lit194 = IntNum.make(iArr6);
        int[] iArr7 = new int[2];
        iArr7[0] = -16777216;
        Lit190 = IntNum.make(iArr7);
        int[] iArr8 = new int[2];
        iArr8[0] = -1;
        Lit186 = IntNum.make(iArr8);
        int[] iArr9 = new int[2];
        iArr9[0] = -16777216;
        Lit185 = IntNum.make(iArr9);
        int[] iArr10 = new int[2];
        iArr10[0] = -1;
        Lit181 = IntNum.make(iArr10);
        int[] iArr11 = new int[2];
        iArr11[0] = -16777216;
        Lit180 = IntNum.make(iArr11);
        int[] iArr12 = new int[2];
        iArr12[0] = -16777216;
        Lit176 = IntNum.make(iArr12);
        int[] iArr13 = new int[2];
        iArr13[0] = -1;
        Lit172 = IntNum.make(iArr13);
        int[] iArr14 = new int[2];
        iArr14[0] = -16777216;
        Lit171 = IntNum.make(iArr14);
        int[] iArr15 = new int[2];
        iArr15[0] = -16777216;
        Lit167 = IntNum.make(iArr15);
        int[] iArr16 = new int[2];
        iArr16[0] = -1;
        Lit163 = IntNum.make(iArr16);
        int[] iArr17 = new int[2];
        iArr17[0] = -16777216;
        Lit159 = IntNum.make(iArr17);
        int[] iArr18 = new int[2];
        iArr18[0] = -1;
        Lit155 = IntNum.make(iArr18);
        int[] iArr19 = new int[2];
        iArr19[0] = -16777216;
        Lit151 = IntNum.make(iArr19);
        int[] iArr20 = new int[2];
        iArr20[0] = -1;
        Lit147 = IntNum.make(iArr20);
        int[] iArr21 = new int[2];
        iArr21[0] = -16777216;
        Lit145 = IntNum.make(iArr21);
        int[] iArr22 = new int[2];
        iArr22[0] = -16777216;
        Lit141 = IntNum.make(iArr22);
        int[] iArr23 = new int[2];
        iArr23[0] = -1;
        Lit137 = IntNum.make(iArr23);
        int[] iArr24 = new int[2];
        iArr24[0] = -16777216;
        Lit136 = IntNum.make(iArr24);
        int[] iArr25 = new int[2];
        iArr25[0] = -16711681;
        Lit130 = IntNum.make(iArr25);
        int[] iArr26 = new int[2];
        iArr26[0] = -16777216;
        Lit128 = IntNum.make(iArr26);
        int[] iArr27 = new int[2];
        iArr27[0] = -16711681;
        Lit119 = IntNum.make(iArr27);
        int[] iArr28 = new int[2];
        iArr28[0] = -16777216;
        Lit117 = IntNum.make(iArr28);
        int[] iArr29 = new int[2];
        iArr29[0] = -1;
        Lit101 = IntNum.make(iArr29);
        int[] iArr30 = new int[2];
        iArr30[0] = -1;
        Lit81 = IntNum.make(iArr30);
        int[] iArr31 = new int[2];
        iArr31[0] = -16777216;
        Lit74 = IntNum.make(iArr31);
        int[] iArr32 = new int[2];
        iArr32[0] = -16777216;
        Lit60 = IntNum.make(iArr32);
        int[] iArr33 = new int[2];
        iArr33[0] = -16777216;
        Lit29 = IntNum.make(iArr33);
        int[] iArr34 = new int[2];
        iArr34[0] = -16777216;
        Lit27 = IntNum.make(iArr34);
        int[] iArr35 = new int[2];
        iArr35[0] = -16777216;
        Lit23 = IntNum.make(iArr35);
        int[] iArr36 = new int[2];
        iArr36[0] = -16777216;
        Lit18 = IntNum.make(iArr36);
    }

    public Screen1() {
        ModuleInfo.register(this);
        frame frame2 = new frame();
        this.get$Mnsimple$Mnname = new ModuleMethod(frame2, 1, Lit237, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.onCreate = new ModuleMethod(frame2, 2, "onCreate", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.android$Mnlog$Mnform = new ModuleMethod(frame2, 3, Lit238, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnform$Mnenvironment = new ModuleMethod(frame2, 4, Lit239, 8194);
        this.lookup$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 5, Lit240, 8193);
        this.is$Mnbound$Mnin$Mnform$Mnenvironment = new ModuleMethod(frame2, 7, Lit241, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.add$Mnto$Mnglobal$Mnvar$Mnenvironment = new ModuleMethod(frame2, 8, Lit242, 8194);
        this.add$Mnto$Mnevents = new ModuleMethod(frame2, 9, Lit243, 8194);
        this.add$Mnto$Mncomponents = new ModuleMethod(frame2, 10, Lit244, 16388);
        this.add$Mnto$Mnglobal$Mnvars = new ModuleMethod(frame2, 11, Lit245, 8194);
        this.add$Mnto$Mnform$Mndo$Mnafter$Mncreation = new ModuleMethod(frame2, 12, Lit246, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.send$Mnerror = new ModuleMethod(frame2, 13, Lit247, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.process$Mnexception = new ModuleMethod(frame2, 14, "process-exception", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        this.dispatchEvent = new ModuleMethod(frame2, 15, Lit248, 16388);
        this.dispatchGenericEvent = new ModuleMethod(frame2, 16, Lit249, 16388);
        this.lookup$Mnhandler = new ModuleMethod(frame2, 17, Lit250, 8194);
        ModuleMethod moduleMethod = new ModuleMethod(frame2, 18, null, 0);
        moduleMethod.setProperty("source-location", "/tmp/runtime7968961605135708732.scm:622");
        lambda$Fn1 = moduleMethod;
        this.$define = new ModuleMethod(frame2, 19, "$define", 0);
        lambda$Fn2 = new ModuleMethod(frame2, 20, null, 0);
        lambda$Fn3 = new ModuleMethod(frame2, 21, null, 0);
        lambda$Fn4 = new ModuleMethod(frame2, 22, null, 0);
        lambda$Fn5 = new ModuleMethod(frame2, 23, null, 0);
        lambda$Fn6 = new ModuleMethod(frame2, 24, null, 0);
        lambda$Fn7 = new ModuleMethod(frame2, 25, null, 0);
        lambda$Fn8 = new ModuleMethod(frame2, 26, null, 0);
        lambda$Fn9 = new ModuleMethod(frame2, 27, null, 0);
        this.Screen1$Initialize = new ModuleMethod(frame2, 28, Lit56, 0);
        lambda$Fn10 = new ModuleMethod(frame2, 29, null, 0);
        lambda$Fn11 = new ModuleMethod(frame2, 30, null, 0);
        lambda$Fn12 = new ModuleMethod(frame2, 31, null, 0);
        lambda$Fn13 = new ModuleMethod(frame2, 32, null, 0);
        this.A2$Click = new ModuleMethod(frame2, 33, Lit71, 0);
        lambda$Fn14 = new ModuleMethod(frame2, 34, null, 0);
        lambda$Fn15 = new ModuleMethod(frame2, 35, null, 0);
        this.A3$Click = new ModuleMethod(frame2, 36, Lit86, 0);
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
        this.A6$Click = new ModuleMethod(frame2, 51, Lit122, 0);
        lambda$Fn30 = new ModuleMethod(frame2, 52, null, 0);
        lambda$Fn31 = new ModuleMethod(frame2, 53, null, 0);
        lambda$Fn32 = new ModuleMethod(frame2, 54, null, 0);
        lambda$Fn33 = new ModuleMethod(frame2, 55, null, 0);
        this.A10$Click = new ModuleMethod(frame2, 56, Lit133, 0);
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
        lambda$Fn60 = new ModuleMethod(frame2, 83, null, 0);
        lambda$Fn61 = new ModuleMethod(frame2, 84, null, 0);
        lambda$Fn62 = new ModuleMethod(frame2, 85, null, 0);
        lambda$Fn63 = new ModuleMethod(frame2, 86, null, 0);
        lambda$Fn64 = new ModuleMethod(frame2, 87, null, 0);
        lambda$Fn65 = new ModuleMethod(frame2, 88, null, 0);
        lambda$Fn66 = new ModuleMethod(frame2, 89, null, 0);
        lambda$Fn67 = new ModuleMethod(frame2, 90, null, 0);
        this.Clock1$Timer = new ModuleMethod(frame2, 91, Lit235, 0);
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
        Screen1 = null;
        this.form$Mnname$Mnsymbol = Lit0;
        this.events$Mnto$Mnregister = LList.Empty;
        this.components$Mnto$Mncreate = LList.Empty;
        this.global$Mnvars$Mnto$Mncreate = LList.Empty;
        this.form$Mndo$Mnafter$Mncreation = LList.Empty;
        runtime.$instance.run();
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit3, runtime.callComponentMethod(Lit4, Lit5, LList.list2("isConnected", Lit6), Lit7)), $result);
        } else {
            addToGlobalVars(Lit3, lambda$Fn2);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit9, runtime.callComponentMethod(Lit4, Lit5, LList.list2("bluetoothData", ""), Lit10)), $result);
        } else {
            addToGlobalVars(Lit9, lambda$Fn3);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit12, ""), $result);
        } else {
            addToGlobalVars(Lit12, lambda$Fn4);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit13, ""), $result);
        } else {
            addToGlobalVars(Lit13, lambda$Fn5);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit14, ""), $result);
        } else {
            addToGlobalVars(Lit14, lambda$Fn6);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit15, ""), $result);
        } else {
            addToGlobalVars(Lit15, lambda$Fn7);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addGlobalVarToCurrentFormEnvironment(Lit16, ""), $result);
        } else {
            addToGlobalVars(Lit16, lambda$Fn8);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit0, Lit17, Lit18, Lit19);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit20, "Pixal", Lit21);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit22, Lit23, Lit19);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit24, "56673328.png", Lit21);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit25, "zoom", Lit21);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit26, Lit27, Lit19);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit28, Lit29, Lit19);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit30, Boolean.TRUE, Lit31);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit32, Boolean.FALSE, Lit31);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit33, "Fixed", Lit21);
            runtime.setAndCoerceProperty$Ex(Lit0, Lit34, "Screen1", Lit21);
            Values.writeValues(runtime.setAndCoerceProperty$Ex(Lit0, Lit35, Boolean.FALSE, Lit31), $result);
        } else {
            addToFormDoAfterCreation(new Promise(lambda$Fn9));
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit56, this.Screen1$Initialize);
        } else {
            addToFormEnvironment(Lit56, this.Screen1$Initialize);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Screen1", "Initialize");
        } else {
            addToEvents(Lit0, Lit57);
        }
        this.A1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit58, Lit59, lambda$Fn10), $result);
        } else {
            addToComponents(Lit0, Lit63, Lit59, lambda$Fn11);
        }
        this.A2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit59, Lit64, Lit65, lambda$Fn12), $result);
        } else {
            addToComponents(Lit59, Lit69, Lit65, lambda$Fn13);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit71, this.A2$Click);
        } else {
            addToFormEnvironment(Lit71, this.A2$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "A2", "Click");
        } else {
            addToEvents(Lit65, Lit72);
        }
        this.A3 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit59, Lit73, Lit47, lambda$Fn14), $result);
        } else {
            addToComponents(Lit59, Lit82, Lit47, lambda$Fn15);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit86, this.A3$Click);
        } else {
            addToFormEnvironment(Lit86, this.A3$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "A3", "Click");
        } else {
            addToEvents(Lit47, Lit72);
        }
        this.HorizontalArrangement2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit87, Lit42, lambda$Fn16), $result);
        } else {
            addToComponents(Lit0, Lit92, Lit42, lambda$Fn17);
        }
        this.VerticalArrangement2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit42, Lit93, Lit94, lambda$Fn18), $result);
        } else {
            addToComponents(Lit42, Lit96, Lit94, lambda$Fn19);
        }
        this.Label10 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit94, Lit97, Lit98, lambda$Fn20), $result);
        } else {
            addToComponents(Lit94, Lit102, Lit98, lambda$Fn21);
        }
        this.VerticalArrangement1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit103, Lit40, lambda$Fn22), $result);
        } else {
            addToComponents(Lit0, Lit105, Lit40, lambda$Fn23);
        }
        this.A4 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit106, Lit107, lambda$Fn24), $result);
        } else {
            addToComponents(Lit40, Lit108, Lit107, lambda$Fn25);
        }
        this.A5 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit107, Lit109, Lit110, lambda$Fn26), $result);
        } else {
            addToComponents(Lit107, Lit114, Lit110, lambda$Fn27);
        }
        this.A6 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit110, Lit115, Lit116, lambda$Fn28), $result);
        } else {
            addToComponents(Lit110, Lit120, Lit116, lambda$Fn29);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit122, this.A6$Click);
        } else {
            addToFormEnvironment(Lit122, this.A6$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "A6", "Click");
        } else {
            addToEvents(Lit116, Lit72);
        }
        this.A9 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit107, Lit123, Lit124, lambda$Fn30), $result);
        } else {
            addToComponents(Lit107, Lit126, Lit124, lambda$Fn31);
        }
        this.A10 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit124, Lit127, Lit54, lambda$Fn32), $result);
        } else {
            addToComponents(Lit124, Lit131, Lit54, lambda$Fn33);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit133, this.A10$Click);
        } else {
            addToFormEnvironment(Lit133, this.A10$Click);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "A10", "Click");
        } else {
            addToEvents(Lit54, Lit72);
        }
        this.A15 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit134, Lit135, lambda$Fn34), $result);
        } else {
            addToComponents(Lit40, Lit138, Lit135, lambda$Fn35);
        }
        this.HorizontalScrollArrangement12 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit139, Lit140, lambda$Fn36), $result);
        } else {
            addToComponents(Lit40, Lit142, Lit140, lambda$Fn37);
        }
        this.ValAccel = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit143, Lit144, lambda$Fn38), $result);
        } else {
            addToComponents(Lit40, Lit148, Lit144, lambda$Fn39);
        }
        this.HorizontalScrollArrangement3 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit149, Lit150, lambda$Fn40), $result);
        } else {
            addToComponents(Lit40, Lit152, Lit150, lambda$Fn41);
        }
        this.Label9 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit153, Lit154, lambda$Fn42), $result);
        } else {
            addToComponents(Lit40, Lit156, Lit154, lambda$Fn43);
        }
        this.HorizontalScrollArrangement11 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit157, Lit158, lambda$Fn44), $result);
        } else {
            addToComponents(Lit40, Lit160, Lit158, lambda$Fn45);
        }
        this.f0ValRgi = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit161, Lit162, lambda$Fn46), $result);
        } else {
            addToComponents(Lit40, Lit164, Lit162, lambda$Fn47);
        }
        this.HorizontalScrollArrangement10 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit165, Lit166, lambda$Fn48), $result);
        } else {
            addToComponents(Lit40, Lit168, Lit166, lambda$Fn49);
        }
        this.A17 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit169, Lit170, lambda$Fn50), $result);
        } else {
            addToComponents(Lit40, Lit173, Lit170, lambda$Fn51);
        }
        this.HorizontalScrollArrangement5 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit174, Lit175, lambda$Fn52), $result);
        } else {
            addToComponents(Lit40, Lit177, Lit175, lambda$Fn53);
        }
        this.ValTemp = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit178, Lit179, lambda$Fn54), $result);
        } else {
            addToComponents(Lit40, Lit182, Lit179, lambda$Fn55);
        }
        this.Label8 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit183, Lit184, lambda$Fn56), $result);
        } else {
            addToComponents(Lit40, Lit187, Lit184, lambda$Fn57);
        }
        this.HorizontalScrollArrangement14 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit188, Lit189, lambda$Fn58), $result);
        } else {
            addToComponents(Lit40, Lit191, Lit189, lambda$Fn59);
        }
        this.Label2 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit192, Lit193, lambda$Fn60), $result);
        } else {
            addToComponents(Lit40, Lit196, Lit193, lambda$Fn61);
        }
        this.HorizontalArrangement1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit197, Lit198, lambda$Fn62), $result);
        } else {
            addToComponents(Lit40, Lit200, Lit198, lambda$Fn63);
        }
        this.ValNive = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit201, Lit202, lambda$Fn64), $result);
        } else {
            addToComponents(Lit40, Lit205, Lit202, lambda$Fn65);
        }
        this.HorizontalScrollArrangement13 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit40, Lit206, Lit207, lambda$Fn66), $result);
        } else {
            addToComponents(Lit40, Lit209, Lit207, lambda$Fn67);
        }
        this.BluetoothClient1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit210, Lit51, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit211, Lit51, Boolean.FALSE);
        }
        this.TinyDB1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit212, Lit4, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit213, Lit4, Boolean.FALSE);
        }
        this.Clock1 = null;
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            Values.writeValues(runtime.addComponentWithinRepl(Lit0, Lit214, Lit43, Boolean.FALSE), $result);
        } else {
            addToComponents(Lit0, Lit215, Lit43, Boolean.FALSE);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            runtime.addToCurrentFormEnvironment(Lit235, this.Clock1$Timer);
        } else {
            addToFormEnvironment(Lit235, this.Clock1$Timer);
        }
        if (runtime.$Stthis$Mnis$Mnthe$Mnrepl$St != Boolean.FALSE) {
            EventDispatcher.registerEventForDelegation((HandlesEventDispatching) runtime.$Stthis$Mnform$St, "Clock1", "Timer");
        } else {
            addToEvents(Lit43, Lit236);
        }
        runtime.initRuntime();
    }

    static Object lambda3() {
        return runtime.callComponentMethod(Lit4, Lit5, LList.list2("isConnected", Lit6), Lit8);
    }

    static Object lambda4() {
        return runtime.callComponentMethod(Lit4, Lit5, LList.list2("bluetoothData", ""), Lit11);
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

    static String lambda8() {
        return "";
    }

    static String lambda9() {
        return "";
    }

    static Object lambda10() {
        runtime.setAndCoerceProperty$Ex(Lit0, Lit17, Lit18, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit20, "Pixal", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit22, Lit23, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit24, "56673328.png", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit25, "zoom", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit26, Lit27, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit28, Lit29, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit30, Boolean.TRUE, Lit31);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit32, Boolean.FALSE, Lit31);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit33, "Fixed", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit0, Lit34, "Screen1", Lit21);
        return runtime.setAndCoerceProperty$Ex(Lit0, Lit35, Boolean.FALSE, Lit31);
    }

    public Object Screen1$Initialize() {
        runtime.setThisForm();
        if (runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.callComponentMethod(Lit4, Lit5, LList.list2("init", Lit6), Lit36), Lit37), Lit38, "=") == Boolean.FALSE) {
            return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Init"), Lit55, "open another screen");
        }
        if (runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St), Lit37), Lit39, "=") != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit40, Lit41, Boolean.TRUE, Lit31);
            runtime.setAndCoerceProperty$Ex(Lit42, Lit41, Boolean.FALSE, Lit31);
            runtime.setAndCoerceProperty$Ex(Lit43, Lit44, Boolean.TRUE, Lit31);
            runtime.setAndCoerceProperty$Ex(Lit43, Lit45, Lit46, Lit19);
            runtime.setAndCoerceProperty$Ex(Lit47, Lit48, "Compte", Lit21);
            if (runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.callComponentMethod(Lit4, Lit5, LList.list2("isBluetooth", Lit6), Lit49), Lit37), Lit50, "=") != Boolean.FALSE) {
                return runtime.callComponentMethod(Lit51, Lit52, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit9, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit53) != Boolean.FALSE ? runtime.setAndCoerceProperty$Ex(Lit54, Lit48, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit9, runtime.$Stthe$Mnnull$Mnvalue$St), Lit21) : Values.empty;
            }
            return Values.empty;
        }
        runtime.setAndCoerceProperty$Ex(Lit47, Lit48, "Se Connecter/S'Inscrire", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit40, Lit41, Boolean.FALSE, Lit31);
        return runtime.setAndCoerceProperty$Ex(Lit42, Lit41, Boolean.TRUE, Lit31);
    }

    static Object lambda11() {
        runtime.setAndCoerceProperty$Ex(Lit59, Lit22, Lit60, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit59, Lit61, Lit62, Lit19);
    }

    static Object lambda12() {
        runtime.setAndCoerceProperty$Ex(Lit59, Lit22, Lit60, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit59, Lit61, Lit62, Lit19);
    }

    static Object lambda13() {
        runtime.setAndCoerceProperty$Ex(Lit65, Lit66, Lit67, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit65, Lit68, "56673328.png", Lit21);
        return runtime.setAndCoerceProperty$Ex(Lit65, Lit61, Lit67, Lit19);
    }

    static Object lambda14() {
        runtime.setAndCoerceProperty$Ex(Lit65, Lit66, Lit67, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit65, Lit68, "56673328.png", Lit21);
        return runtime.setAndCoerceProperty$Ex(Lit65, Lit61, Lit67, Lit19);
    }

    public Object A2$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen5"), Lit70, "open another screen");
    }

    static Object lambda15() {
        runtime.setAndCoerceProperty$Ex(Lit47, Lit22, Lit74, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit47, Lit75, Boolean.TRUE, Lit31);
        runtime.setAndCoerceProperty$Ex(Lit47, Lit76, Lit77, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit47, Lit48, "Compte", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit47, Lit78, Lit79, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit47, Lit80, Lit81, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit47, Lit61, Lit62, Lit19);
    }

    static Object lambda16() {
        runtime.setAndCoerceProperty$Ex(Lit47, Lit22, Lit74, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit47, Lit75, Boolean.TRUE, Lit31);
        runtime.setAndCoerceProperty$Ex(Lit47, Lit76, Lit77, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit47, Lit48, "Compte", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit47, Lit78, Lit79, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit47, Lit80, Lit81, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit47, Lit61, Lit62, Lit19);
    }

    public Object A3$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit3, runtime.$Stthe$Mnnull$Mnvalue$St), Lit6), Lit83, "=") != Boolean.FALSE ? runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen3"), Lit84, "open another screen") : runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Compte"), Lit85, "open another screen");
    }

    static Object lambda17() {
        runtime.setAndCoerceProperty$Ex(Lit42, Lit88, Lit89, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit42, Lit90, Lit79, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit42, Lit22, Lit91, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit42, Lit66, Lit62, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit42, Lit41, Boolean.FALSE, Lit31);
        return runtime.setAndCoerceProperty$Ex(Lit42, Lit61, Lit62, Lit19);
    }

    static Object lambda18() {
        runtime.setAndCoerceProperty$Ex(Lit42, Lit88, Lit89, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit42, Lit90, Lit79, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit42, Lit22, Lit91, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit42, Lit66, Lit62, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit42, Lit41, Boolean.FALSE, Lit31);
        return runtime.setAndCoerceProperty$Ex(Lit42, Lit61, Lit62, Lit19);
    }

    static Object lambda19() {
        runtime.setAndCoerceProperty$Ex(Lit94, Lit88, Lit89, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit94, Lit90, Lit79, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit94, Lit22, Lit95, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit94, Lit66, Lit62, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit94, Lit61, Lit62, Lit19);
    }

    static Object lambda20() {
        runtime.setAndCoerceProperty$Ex(Lit94, Lit88, Lit89, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit94, Lit90, Lit79, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit94, Lit22, Lit95, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit94, Lit66, Lit62, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit94, Lit61, Lit62, Lit19);
    }

    static Object lambda21() {
        runtime.setAndCoerceProperty$Ex(Lit98, Lit76, Lit99, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit98, Lit100, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit98, Lit66, Lit62, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit98, Lit48, "Veuillez vous connecter pour pouvoir utiliser l'application VRC", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit98, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit98, Lit80, Lit101, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit98, Lit61, Lit62, Lit19);
    }

    static Object lambda22() {
        runtime.setAndCoerceProperty$Ex(Lit98, Lit76, Lit99, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit98, Lit100, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit98, Lit66, Lit62, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit98, Lit48, "Veuillez vous connecter pour pouvoir utiliser l'application VRC", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit98, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit98, Lit80, Lit101, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit98, Lit61, Lit62, Lit19);
    }

    static Object lambda23() {
        runtime.setAndCoerceProperty$Ex(Lit40, Lit22, Lit104, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit40, Lit61, Lit62, Lit19);
    }

    static Object lambda24() {
        runtime.setAndCoerceProperty$Ex(Lit40, Lit22, Lit104, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit40, Lit61, Lit62, Lit19);
    }

    static Object lambda25() {
        return runtime.setAndCoerceProperty$Ex(Lit107, Lit61, Lit62, Lit19);
    }

    static Object lambda26() {
        return runtime.setAndCoerceProperty$Ex(Lit107, Lit61, Lit62, Lit19);
    }

    static Object lambda27() {
        runtime.setAndCoerceProperty$Ex(Lit110, Lit88, Lit79, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit110, Lit111, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit110, Lit112, Lit6, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit110, Lit61, Lit113, Lit19);
    }

    static Object lambda28() {
        runtime.setAndCoerceProperty$Ex(Lit110, Lit88, Lit79, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit110, Lit111, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit110, Lit112, Lit6, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit110, Lit61, Lit113, Lit19);
    }

    static Object lambda29() {
        runtime.setAndCoerceProperty$Ex(Lit116, Lit22, Lit117, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit116, Lit75, Boolean.TRUE, Lit31);
        runtime.setAndCoerceProperty$Ex(Lit116, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit116, Lit48, "Ajouter VRC", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit116, Lit80, Lit119, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit116, Lit61, Lit62, Lit19);
    }

    static Object lambda30() {
        runtime.setAndCoerceProperty$Ex(Lit116, Lit22, Lit117, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit116, Lit75, Boolean.TRUE, Lit31);
        runtime.setAndCoerceProperty$Ex(Lit116, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit116, Lit48, "Ajouter VRC", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit116, Lit80, Lit119, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit116, Lit61, Lit62, Lit19);
    }

    public Object A6$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen6"), Lit121, "open another screen");
    }

    static Object lambda31() {
        runtime.setAndCoerceProperty$Ex(Lit124, Lit111, Lit6, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit124, Lit112, Lit6, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit124, Lit61, Lit125, Lit19);
    }

    static Object lambda32() {
        runtime.setAndCoerceProperty$Ex(Lit124, Lit111, Lit6, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit124, Lit112, Lit6, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit124, Lit61, Lit125, Lit19);
    }

    static Object lambda33() {
        runtime.setAndCoerceProperty$Ex(Lit54, Lit22, Lit128, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit75, Boolean.TRUE, Lit31);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit66, Lit62, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit129, Lit79, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit48, "Bluetooth", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit80, Lit130, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit54, Lit61, Lit62, Lit19);
    }

    static Object lambda34() {
        runtime.setAndCoerceProperty$Ex(Lit54, Lit22, Lit128, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit75, Boolean.TRUE, Lit31);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit66, Lit62, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit129, Lit79, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit48, "Bluetooth", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit54, Lit80, Lit130, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit54, Lit61, Lit62, Lit19);
    }

    public Object A10$Click() {
        runtime.setThisForm();
        return runtime.callYailPrimitive(runtime.open$Mnanother$Mnscreen, LList.list1("Screen2"), Lit132, "open another screen");
    }

    static Object lambda35() {
        runtime.setAndCoerceProperty$Ex(Lit135, Lit22, Lit136, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit48, "Accélération :", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit80, Lit137, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit135, Lit61, Lit62, Lit19);
    }

    static Object lambda36() {
        runtime.setAndCoerceProperty$Ex(Lit135, Lit22, Lit136, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit48, "Accélération :", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit135, Lit80, Lit137, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit135, Lit61, Lit62, Lit19);
    }

    static Object lambda37() {
        runtime.setAndCoerceProperty$Ex(Lit140, Lit22, Lit141, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit140, Lit66, Lit67, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit140, Lit61, Lit62, Lit19);
    }

    static Object lambda38() {
        runtime.setAndCoerceProperty$Ex(Lit140, Lit22, Lit141, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit140, Lit66, Lit67, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit140, Lit61, Lit62, Lit19);
    }

    static Object lambda39() {
        runtime.setAndCoerceProperty$Ex(Lit144, Lit22, Lit145, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit144, Lit76, Lit146, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit144, Lit48, "0", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit144, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit144, Lit80, Lit147, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit144, Lit61, Lit62, Lit19);
    }

    static Object lambda40() {
        runtime.setAndCoerceProperty$Ex(Lit144, Lit22, Lit145, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit144, Lit76, Lit146, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit144, Lit48, "0", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit144, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit144, Lit80, Lit147, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit144, Lit61, Lit62, Lit19);
    }

    static Object lambda41() {
        runtime.setAndCoerceProperty$Ex(Lit150, Lit22, Lit151, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit150, Lit66, Lit67, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit150, Lit61, Lit62, Lit19);
    }

    static Object lambda42() {
        runtime.setAndCoerceProperty$Ex(Lit150, Lit22, Lit151, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit150, Lit66, Lit67, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit150, Lit61, Lit62, Lit19);
    }

    static Object lambda43() {
        runtime.setAndCoerceProperty$Ex(Lit154, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit154, Lit48, "Régime moteur :", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit154, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit154, Lit80, Lit155, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit154, Lit61, Lit62, Lit19);
    }

    static Object lambda44() {
        runtime.setAndCoerceProperty$Ex(Lit154, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit154, Lit48, "Régime moteur :", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit154, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit154, Lit80, Lit155, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit154, Lit61, Lit62, Lit19);
    }

    static Object lambda45() {
        runtime.setAndCoerceProperty$Ex(Lit158, Lit22, Lit159, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit158, Lit66, Lit67, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit158, Lit61, Lit62, Lit19);
    }

    static Object lambda46() {
        runtime.setAndCoerceProperty$Ex(Lit158, Lit22, Lit159, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit158, Lit66, Lit67, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit158, Lit61, Lit62, Lit19);
    }

    static Object lambda47() {
        runtime.setAndCoerceProperty$Ex(Lit162, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit162, Lit48, "0", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit162, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit162, Lit80, Lit163, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit162, Lit61, Lit62, Lit19);
    }

    static Object lambda48() {
        runtime.setAndCoerceProperty$Ex(Lit162, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit162, Lit48, "0", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit162, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit162, Lit80, Lit163, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit162, Lit61, Lit62, Lit19);
    }

    static Object lambda49() {
        runtime.setAndCoerceProperty$Ex(Lit166, Lit22, Lit167, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit166, Lit66, Lit67, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit166, Lit61, Lit62, Lit19);
    }

    static Object lambda50() {
        runtime.setAndCoerceProperty$Ex(Lit166, Lit22, Lit167, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit166, Lit66, Lit67, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit166, Lit61, Lit62, Lit19);
    }

    static Object lambda51() {
        runtime.setAndCoerceProperty$Ex(Lit170, Lit22, Lit171, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit170, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit170, Lit48, "Température moteur :", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit170, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit170, Lit80, Lit172, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit170, Lit61, Lit62, Lit19);
    }

    static Object lambda52() {
        runtime.setAndCoerceProperty$Ex(Lit170, Lit22, Lit171, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit170, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit170, Lit48, "Température moteur :", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit170, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit170, Lit80, Lit172, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit170, Lit61, Lit62, Lit19);
    }

    static Object lambda53() {
        runtime.setAndCoerceProperty$Ex(Lit175, Lit22, Lit176, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit175, Lit61, Lit62, Lit19);
    }

    static Object lambda54() {
        runtime.setAndCoerceProperty$Ex(Lit175, Lit22, Lit176, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit175, Lit61, Lit62, Lit19);
    }

    static Object lambda55() {
        runtime.setAndCoerceProperty$Ex(Lit179, Lit22, Lit180, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit179, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit179, Lit48, "0", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit179, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit179, Lit80, Lit181, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit179, Lit61, Lit62, Lit19);
    }

    static Object lambda56() {
        runtime.setAndCoerceProperty$Ex(Lit179, Lit22, Lit180, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit179, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit179, Lit48, "0", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit179, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit179, Lit80, Lit181, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit179, Lit61, Lit62, Lit19);
    }

    static Object lambda57() {
        runtime.setAndCoerceProperty$Ex(Lit184, Lit22, Lit185, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit184, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit184, Lit48, "°C", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit184, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit184, Lit80, Lit186, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit184, Lit61, Lit62, Lit19);
    }

    static Object lambda58() {
        runtime.setAndCoerceProperty$Ex(Lit184, Lit22, Lit185, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit184, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit184, Lit48, "°C", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit184, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit184, Lit80, Lit186, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit184, Lit61, Lit62, Lit19);
    }

    static Object lambda59() {
        runtime.setAndCoerceProperty$Ex(Lit189, Lit22, Lit190, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit189, Lit66, Lit67, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit189, Lit61, Lit62, Lit19);
    }

    static Object lambda60() {
        runtime.setAndCoerceProperty$Ex(Lit189, Lit22, Lit190, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit189, Lit66, Lit67, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit189, Lit61, Lit62, Lit19);
    }

    static Object lambda61() {
        runtime.setAndCoerceProperty$Ex(Lit193, Lit22, Lit194, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit193, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit193, Lit48, "Niveau de batterie :", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit193, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit193, Lit80, Lit195, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit193, Lit61, Lit62, Lit19);
    }

    static Object lambda62() {
        runtime.setAndCoerceProperty$Ex(Lit193, Lit22, Lit194, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit193, Lit76, Lit118, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit193, Lit48, "Niveau de batterie :", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit193, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit193, Lit80, Lit195, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit193, Lit61, Lit62, Lit19);
    }

    static Object lambda63() {
        runtime.setAndCoerceProperty$Ex(Lit198, Lit22, Lit199, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit198, Lit66, Lit67, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit198, Lit61, Lit62, Lit19);
    }

    static Object lambda64() {
        runtime.setAndCoerceProperty$Ex(Lit198, Lit22, Lit199, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit198, Lit66, Lit67, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit198, Lit61, Lit62, Lit19);
    }

    static Object lambda65() {
        runtime.setAndCoerceProperty$Ex(Lit202, Lit22, Lit203, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit202, Lit76, Lit146, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit202, Lit48, "0", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit202, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit202, Lit80, Lit204, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit202, Lit61, Lit62, Lit19);
    }

    static Object lambda66() {
        runtime.setAndCoerceProperty$Ex(Lit202, Lit22, Lit203, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit202, Lit76, Lit146, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit202, Lit48, "0", Lit21);
        runtime.setAndCoerceProperty$Ex(Lit202, Lit78, Lit37, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit202, Lit80, Lit204, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit202, Lit61, Lit62, Lit19);
    }

    static Object lambda67() {
        runtime.setAndCoerceProperty$Ex(Lit207, Lit22, Lit208, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit207, Lit66, Lit67, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit207, Lit61, Lit62, Lit19);
    }

    static Object lambda68() {
        runtime.setAndCoerceProperty$Ex(Lit207, Lit22, Lit208, Lit19);
        runtime.setAndCoerceProperty$Ex(Lit207, Lit66, Lit67, Lit19);
        return runtime.setAndCoerceProperty$Ex(Lit207, Lit61, Lit62, Lit19);
    }

    public Object Clock1$Timer() {
        runtime.setThisForm();
        if (runtime.callYailPrimitive(runtime.yail$Mnequal$Qu, LList.list2(runtime.callComponentMethod(Lit4, Lit5, LList.list2("isBluetooth", Lit6), Lit216), Lit37), Lit217, "=") == Boolean.FALSE) {
            return Values.empty;
        }
        if (runtime.callYailPrimitive(Scheme.numGrt, LList.list2(runtime.callComponentMethod(Lit51, Lit218, LList.Empty, LList.Empty), Lit6), Lit219, ">") == Boolean.FALSE) {
            return Values.empty;
        }
        runtime.addGlobalVarToCurrentFormEnvironment(Lit12, runtime.callComponentMethod(Lit51, Lit220, LList.list1(runtime.callComponentMethod(Lit51, Lit218, LList.Empty, LList.Empty)), Lit221));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit13, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.string$Mnsplit, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit12, runtime.$Stthe$Mnnull$Mnvalue$St), ","), Lit222, "split"), Lit37), Lit223, "select list item"));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit14, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.string$Mnsplit, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit12, runtime.$Stthe$Mnnull$Mnvalue$St), ","), Lit224, "split"), Lit79), Lit225, "select list item"));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit15, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.string$Mnsplit, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit12, runtime.$Stthe$Mnnull$Mnvalue$St), ","), Lit226, "split"), Lit89), Lit227, "select list item"));
        runtime.addGlobalVarToCurrentFormEnvironment(Lit16, runtime.callYailPrimitive(runtime.yail$Mnlist$Mnget$Mnitem, LList.list2(runtime.callYailPrimitive(runtime.string$Mnsplit, LList.list2(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit12, runtime.$Stthe$Mnnull$Mnvalue$St), ","), Lit228, "split"), Lit229), Lit230, "select list item"));
        if (runtime.callYailPrimitive(runtime.is$Mnnumber$Qu, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit13, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit231, "is a number?") != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit144, Lit48, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit13, runtime.$Stthe$Mnnull$Mnvalue$St), Lit21);
        }
        if (runtime.callYailPrimitive(runtime.is$Mnnumber$Qu, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit14, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit232, "is a number?") != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit162, Lit48, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit14, runtime.$Stthe$Mnnull$Mnvalue$St), Lit21);
        }
        if (runtime.callYailPrimitive(runtime.is$Mnnumber$Qu, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit15, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit233, "is a number?") != Boolean.FALSE) {
            runtime.setAndCoerceProperty$Ex(Lit179, Lit48, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit15, runtime.$Stthe$Mnnull$Mnvalue$St), Lit21);
        }
        return runtime.callYailPrimitive(runtime.is$Mnnumber$Qu, LList.list1(runtime.lookupGlobalVarInCurrentFormEnvironment(Lit16, runtime.$Stthe$Mnnull$Mnvalue$St)), Lit234, "is a number?") != Boolean.FALSE ? runtime.setAndCoerceProperty$Ex(Lit202, Lit48, runtime.lookupGlobalVarInCurrentFormEnvironment(Lit16, runtime.$Stthe$Mnnull$Mnvalue$St), Lit21) : Values.empty;
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
