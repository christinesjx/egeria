/* SPDX-License-Identifier: Apache 2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.openmetadata.accessservices.connectedasset.client;


import org.odpi.openmetadata.accessservices.connectedasset.ffdc.ConnectedAssetErrorCode;
import org.odpi.openmetadata.accessservices.connectedasset.rest.ExternalIdentifiersResponse;
import org.odpi.openmetadata.frameworks.connectors.ffdc.PropertyServerException;
import org.odpi.openmetadata.frameworks.connectors.properties.AssetDescriptor;
import org.odpi.openmetadata.frameworks.connectors.properties.AssetExternalIdentifier;
import org.odpi.openmetadata.frameworks.connectors.properties.AssetExternalIdentifiers;
import org.odpi.openmetadata.frameworks.connectors.properties.AssetPropertyBase;
import org.odpi.openmetadata.frameworks.connectors.properties.beans.ExternalIdentifier;

import java.util.ArrayList;
import java.util.List;


/**
 * ConnectedAssetExternalIdentifiers provides the open metadata concrete implementation of the
 * Open Connector Framework (OCF) AssetExternalIdentifiers abstract class.
 * Its role is to query the property servers (metadata repository cohort) to extract external identifiers
 * related to the connected asset.
 */
public class ConnectedAssetExternalIdentifiers extends AssetExternalIdentifiers
{
    private String              serverName;
    private String              userId;
    private String              omasServerURL;
    private String              assetGUID;
    private ConnectedAsset      connectedAsset;


    /**
     * Typical constructor creates an iterator with the supplied list of elements.
     *
     * @param serverName  name of the server.
     * @param userId user id to use on server calls.
     * @param omasServerURL url root of the server to use.
     * @param assetGUID unique identifier of the asset.
     * @param parentAsset descriptor of parent asset.
     * @param totalElementCount the total number of elements to process.  A negative value is converted to 0.
     * @param maxCacheSize maximum number of elements that should be retrieved from the property server and
     *                     cached in the element list at any one time.  If a number less than one is supplied, 1 is used.
     */
    ConnectedAssetExternalIdentifiers(String              serverName,
                                      String              userId,
                                      String              omasServerURL,
                                      String              assetGUID,
                                      ConnectedAsset      parentAsset,
                                      int                 totalElementCount,
                                      int                 maxCacheSize)
    {
        super(parentAsset, totalElementCount, maxCacheSize);

        this.serverName      = serverName;
        this.userId          = userId;
        this.omasServerURL   = omasServerURL;
        this.assetGUID       = assetGUID;
        this.connectedAsset  = parentAsset;
    }


    /**
     * Copy/clone constructor.  Used to reset iterator element pointer to 0;
     *
     * @param parentAsset descriptor of parent asset
     * @param template type-specific iterator to copy; null to create an empty iterator
     */
    private ConnectedAssetExternalIdentifiers(ConnectedAsset   parentAsset, ConnectedAssetExternalIdentifiers template)
    {
        super(parentAsset, template);

        if (template != null)
        {
            this.serverName = template.serverName;
            this.userId = template.userId;
            this.omasServerURL = template.omasServerURL;
            this.assetGUID = template.assetGUID;
            this.connectedAsset = parentAsset;
        }
    }


    /**
     * Clones this iterator.
     *
     * @param parentAsset descriptor of parent asset
     * @return new cloned object.
     */
    protected  AssetExternalIdentifiers cloneIterator(AssetDescriptor parentAsset)
    {
        return new ConnectedAssetExternalIdentifiers(connectedAsset, this);
    }



    /**
     * Method implemented by a subclass that ensures the cloning process is a deep clone.
     *
     * @param parentAsset descriptor of parent asset
     * @param template object to clone
     * @return new cloned object.
     */
    protected  AssetPropertyBase cloneElement(AssetDescriptor  parentAsset, AssetPropertyBase template)
    {
        return new AssetExternalIdentifier(parentAsset, (AssetExternalIdentifier)template);
    }


    /**
     * Method implemented by subclass to retrieve the next cached list of elements.
     *
     * @param cacheStartPointer where to start the cache.
     * @param maximumSize maximum number of elements in the cache.
     * @return list of elements corresponding to the supplied cache pointers.
     * @throws PropertyServerException there is a problem retrieving elements from the property (metadata) server.
     */
    protected  List<AssetPropertyBase> getCachedList(int  cacheStartPointer,
                                                     int  maximumSize) throws PropertyServerException
    {
        final String   methodName = "AssetExternalIdentifiers.getCachedList";
        final String   urlTemplate = "/servers/{0}/open-metadata/access-services/connected-asset/users/{1}/assets/{2}/external-identifiers?elementStart={3}&maxElements={4}";

        connectedAsset.validateOMASServerURL(methodName);

        try
        {
            ExternalIdentifiersResponse restResult = (ExternalIdentifiersResponse)connectedAsset.callGetRESTCall(methodName,
                                                                                                                 ExternalIdentifiersResponse.class,
                                                                                                                 omasServerURL + urlTemplate,
                                                                                                                 serverName,
                                                                                                                 userId,
                                                                                                                 assetGUID,
                                                                                                                 cacheStartPointer,
                                                                                                                 maximumSize);

            connectedAsset.detectAndThrowInvalidParameterException(methodName, restResult);
            connectedAsset.detectAndThrowUnrecognizedAssetGUIDException(methodName, restResult);
            connectedAsset.detectAndThrowUserNotAuthorizedException(methodName, restResult);
            connectedAsset.detectAndThrowPropertyServerException(methodName, restResult);

            List<ExternalIdentifier>  beans = restResult.getList();
            if ((beans == null) || (beans.isEmpty()))
            {
                return null;
            }
            else
            {
                List<AssetPropertyBase>   resultList = new ArrayList<>();

                for (ExternalIdentifier  bean : beans)
                {
                    if (bean != null)
                    {
                        resultList.add(new AssetExternalIdentifier(connectedAsset, bean));
                    }
                }

                return resultList;
            }
        }
        catch (Throwable  error)
        {
            ConnectedAssetErrorCode errorCode = ConnectedAssetErrorCode.EXCEPTION_RESPONSE_FROM_API;
            String errorMessage = errorCode.getErrorMessageId() + errorCode.getFormattedErrorMessage(error.getClass().getName(),
                                                                                                     methodName,
                                                                                                     omasServerURL,
                                                                                                     error.getMessage());

            throw new PropertyServerException(errorCode.getHTTPErrorCode(),
                                              this.getClass().getName(),
                                              methodName,
                                              errorMessage,
                                              errorCode.getSystemAction(),
                                              errorCode.getUserAction(),
                                              error);
        }
    }
}
