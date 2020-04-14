package com.google.appinventor.components.runtime;

import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.errors.DispatchableError;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.GeometryUtil;
import com.google.appinventor.components.runtime.util.MapFactory.MapCircle;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureContainer;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureType;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor;
import com.google.appinventor.components.runtime.util.MapFactory.MapLineString;
import com.google.appinventor.components.runtime.util.MapFactory.MapMarker;
import com.google.appinventor.components.runtime.util.MapFactory.MapPolygon;
import com.google.appinventor.components.runtime.util.MapFactory.MapRectangle;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.locationtech.jts.geom.Geometry;
import org.osmdroid.util.GeoPoint;

@SimpleObject
@DesignerComponent(category = ComponentCategory.MAPS, description = "Polygon", version = 2)
public class Polygon extends PolygonBase implements MapPolygon {
    private static final String TAG = Polygon.class.getSimpleName();
    private static final MapFeatureVisitor<Double> distanceComputation = new MapFeatureVisitor<Double>() {
        public Double visit(MapMarker marker, Object... arguments) {
            if (arguments[1].booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(marker, (MapPolygon) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(marker, (MapPolygon) arguments[0]));
        }

        public Double visit(MapLineString lineString, Object... arguments) {
            if (arguments[1].booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(lineString, (MapPolygon) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(lineString, (MapPolygon) arguments[0]));
        }

        public Double visit(MapPolygon polygon, Object... arguments) {
            if (arguments[1].booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(polygon, (MapPolygon) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(polygon, (MapPolygon) arguments[0]));
        }

        public Double visit(MapCircle circle, Object... arguments) {
            if (arguments[1].booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids((MapPolygon) arguments[0], circle));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges((MapPolygon) arguments[0], circle));
        }

        public Double visit(MapRectangle rectangle, Object... arguments) {
            if (arguments[1].booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids((MapPolygon) arguments[0], rectangle));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges((MapPolygon) arguments[0], rectangle));
        }
    };
    private List<List<List<GeoPoint>>> holePoints = new ArrayList();
    private boolean initialized = false;
    private boolean multipolygon = false;
    private List<List<GeoPoint>> points = new ArrayList();

    public Polygon(MapFeatureContainer container) {
        super(container, distanceComputation);
        container.addFeature(this);
    }

    public void Initialize() {
        this.initialized = true;
        clearGeometry();
        this.map.getController().updateFeaturePosition((MapPolygon) this);
        this.map.getController().updateFeatureHoles(this);
        this.map.getController().updateFeatureText(this);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns the type of the feature. For polygons, this returns the text \"Polygon\".")
    public String Type() {
        return MapFeatureType.TYPE_POLYGON;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Gets or sets the sequence of points used to draw the polygon.")
    public YailList Points() {
        if (this.points.isEmpty()) {
            return YailList.makeEmptyList();
        }
        if (!this.multipolygon) {
            return GeometryUtil.pointsListToYailList((List) this.points.get(0));
        }
        List<YailList> result = new LinkedList<>();
        for (List<GeoPoint> part : this.points) {
            result.add(GeometryUtil.pointsListToYailList(part));
        }
        return YailList.makeList((List) result);
    }

    @SimpleProperty
    public void Points(YailList points2) {
        try {
            if (GeometryUtil.isPolygon(points2)) {
                this.multipolygon = false;
                this.points.clear();
                this.points.add(GeometryUtil.pointsFromYailList(points2));
            } else if (GeometryUtil.isMultiPolygon(points2)) {
                this.multipolygon = true;
                this.points = GeometryUtil.multiPolygonFromYailList(points2);
            } else {
                throw new DispatchableError(ErrorMessages.ERROR_POLYGON_PARSE_ERROR, "Unable to determine the structure of the points argument.");
            }
            if (this.initialized) {
                clearGeometry();
                this.map.getController().updateFeaturePosition((MapPolygon) this);
            }
        } catch (DispatchableError e) {
            this.container.$form().dispatchErrorOccurredEvent(this, "Points", e.getErrorCode(), e.getArguments());
        }
    }

    @DesignerProperty(editorType = "textArea")
    @SimpleProperty(description = "Constructs a polygon from the given list of coordinates.")
    public void PointsFromString(String pointString) {
        boolean z;
        if (TextUtils.isEmpty(pointString)) {
            this.points = new ArrayList();
            this.map.getController().updateFeaturePosition((MapPolygon) this);
            return;
        }
        try {
            JSONArray content = new JSONArray(pointString);
            if (content.length() == 0) {
                this.points = new ArrayList();
                this.multipolygon = false;
                this.map.getController().updateFeaturePosition((MapPolygon) this);
                return;
            }
            this.points = GeometryUtil.multiPolygonToList(content);
            if (this.points.size() > 1) {
                z = true;
            } else {
                z = false;
            }
            this.multipolygon = z;
            if (this.initialized) {
                clearGeometry();
                this.map.getController().updateFeaturePosition((MapPolygon) this);
            }
        } catch (JSONException e) {
            this.container.$form().dispatchErrorOccurredEvent(this, "PointsFromString", ErrorMessages.ERROR_POLYGON_PARSE_ERROR, e.getMessage());
        } catch (DispatchableError e2) {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "PointsFromString", e2.getErrorCode(), e2.getArguments());
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Gets or sets the sequence of points used to draw holes in the polygon.")
    public YailList HolePoints() {
        if (this.holePoints.isEmpty()) {
            return YailList.makeEmptyList();
        }
        if (!this.multipolygon) {
            return GeometryUtil.multiPolygonToYailList((List) this.holePoints.get(0));
        }
        List<YailList> result = new LinkedList<>();
        for (List<List<GeoPoint>> polyholes : this.holePoints) {
            result.add(GeometryUtil.multiPolygonToYailList(polyholes));
        }
        return YailList.makeList((List) result);
    }

    @SimpleProperty
    public void HolePoints(YailList points2) {
        try {
            if (points2.size() == 0) {
                this.holePoints = new ArrayList();
            } else if (this.multipolygon) {
                this.holePoints = GeometryUtil.multiPolygonHolesFromYailList(points2);
            } else if (GeometryUtil.isMultiPolygon(points2)) {
                List<List<List<GeoPoint>>> holes = new ArrayList<>();
                holes.add(GeometryUtil.multiPolygonFromYailList(points2));
                this.holePoints = holes;
            } else {
                throw new DispatchableError(ErrorMessages.ERROR_POLYGON_PARSE_ERROR, "Unable to determine the structure of the points argument.");
            }
            if (this.initialized) {
                clearGeometry();
                this.map.getController().updateFeatureHoles(this);
            }
        } catch (DispatchableError e) {
            this.container.$form().dispatchErrorOccurredEvent(this, "HolePoints", e.getErrorCode(), e.getArguments());
        }
    }

    @DesignerProperty(editorType = "textArea")
    @SimpleProperty(description = "Constructs holes in a polygon from a given list of coordinates per hole.")
    public void HolePointsFromString(String pointString) {
        if (TextUtils.isEmpty(pointString)) {
            this.holePoints = new ArrayList();
            this.map.getController().updateFeatureHoles(this);
            return;
        }
        try {
            JSONArray content = new JSONArray(pointString);
            if (content.length() == 0) {
                this.holePoints = new ArrayList();
                this.map.getController().updateFeatureHoles(this);
                return;
            }
            this.holePoints = GeometryUtil.multiPolygonHolesToList(content);
            if (this.initialized) {
                clearGeometry();
                this.map.getController().updateFeatureHoles(this);
            }
            Log.d(TAG, "Points: " + this.points);
        } catch (JSONException e) {
            Log.e(TAG, "Unable to parse point string", e);
            this.container.$form().dispatchErrorOccurredEvent(this, "HolePointsFromString", ErrorMessages.ERROR_POLYGON_PARSE_ERROR, e.getMessage());
        }
    }

    @SimpleFunction(description = "Returns the centroid of the Polygon as a (latitude, longitude) pair.")
    public YailList Centroid() {
        return super.Centroid();
    }

    public List<List<GeoPoint>> getPoints() {
        return this.points;
    }

    public List<List<List<GeoPoint>>> getHolePoints() {
        return this.holePoints;
    }

    public <T> T accept(MapFeatureVisitor<T> visitor, Object... arguments) {
        return visitor.visit((MapPolygon) this, arguments);
    }

    /* access modifiers changed from: protected */
    public Geometry computeGeometry() {
        return GeometryUtil.createGeometry(this.points, this.holePoints);
    }

    public void updatePoints(List<List<GeoPoint>> points2) {
        this.points.clear();
        this.points.addAll(points2);
        clearGeometry();
    }

    public void updateHolePoints(List<List<List<GeoPoint>>> points2) {
        this.holePoints.clear();
        this.holePoints.addAll(points2);
        clearGeometry();
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public boolean isInitialized() {
        return this.initialized;
    }
}
