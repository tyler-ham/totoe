package name.pehl.totoe.client.internal;

import java.util.ArrayList;
import java.util.List;

import name.pehl.totoe.client.Node;
import name.pehl.totoe.client.NodeType;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author $Author$
 * @version $Date$ $Revision: 623
 *          $
 */
public class NodeImpl implements Node
{
    private JavaScriptObject jsNode;


    // ----------------------------------------------------------- constructors

    private NodeImpl(JavaScriptObject jso)
    {
        this.jsNode = jso;
    }


    public static Node create(JavaScriptObject jso)
    {
        return new NodeImpl(jso);
    }


    // -------------------------------------------------- basic node operations

    @Override
    public native String getNodeName() /*-{
        var node = this.@name.pehl.totoe.client.internal.NodeImpl::jsNode;
        return node.nodeName;
    }-*/;


    @Override
    public native String getNodeValue() /*-{
        var node = this.@name.pehl.totoe.client.internal.NodeImpl::jsNode;
        return node.nodeValue;
    }-*/;


    @Override
    public native NodeType getNodeType() /*-{
        var node = this.@name.pehl.totoe.client.internal.NodeImpl::jsNode;
        return @name.pehl.totoe.client.NodeType::typeOf(I)(node.nodeType);
    }-*/;


    @Override
    public native String getAttribute(String name) /*-{
        var node = this.@name.pehl.totoe.client.internal.NodeImpl::jsNode;
        return node.getAttribute(name);
    }-*/;


    @Override
    public Node getRoot()
    {
        if (NodeType.DOCUMENT.equals(getNodeType()))
        {
            List<Node> children = getChildNodes();
            for (Node child : children)
            {
                if (NodeType.ELEMENT.equals(child.getNodeType()))
                {
                    return child;
                }
            }
        }
        return null;
    }


    // ----------------------------------------------- nodes as direct children

    @Override
    public List<Node> getChildNodes()
    {
        List<Node> result = new ArrayList<Node>();
        getChildNodesImpl(result);
        return result;
    }


    private native void getChildNodesImpl(List<Node> result) /*-{
        var node = this.@name.pehl.totoe.client.internal.NodeImpl::jsNode;
        var nodes = node.childNodes;
        if (nodes != null && nodes.length != 0)
        {
            for (var i = 0; i < nodes.length; i++) 
            {
                var currentNode = nodes[i];
                result.@java.util.List::add(Ljava/lang/Object;)
                    (@name.pehl.totoe.client.internal.NodeImpl::create(Lcom/google/gwt/core/client/JavaScriptObject;)
                        (currentNode));
            }
        }
    }-*/;


    // ------------------------------------------------------- node(s) by xpath

    @Override
    public List<Node> selectNodes(String xpath)
    {
        List<Node> result = new ArrayList<Node>();
        selectNodesImpl(xpath, result);
        return result;
    }


    private native void selectNodesImpl(String xpath, List<Node> result) /*-{
        var node = this.@name.pehl.totoe.client.internal.NodeImpl::jsNode;
        var nodes = node.selectNodes(xpath);
        if (nodes != null && nodes.length != 0)
        {
            for (var i = 0; i < nodes.length; i++) 
            {
                var currentNode = nodes[i];
                result.@java.util.List::add(Ljava/lang/Object;)
                    (@name.pehl.totoe.client.internal.NodeImpl::create(Lcom/google/gwt/core/client/JavaScriptObject;)
                        (currentNode));
            }
        }
    }-*/;


    @Override
    public native Node selectNode(String xpath) /*-{
        var node = this.@name.pehl.totoe.client.internal.NodeImpl::jsNode;
        var singleNode = node.selectSingleNode(xpath);
        if (n == null || n == undefined)
        {
            return null;
        }
        return @name.pehl.totoe.client.internal.NodeImpl::create(Lcom/google/gwt/core/client/JavaScriptObject;)(singleNode);
    }-*/;


    // ------------------------------------------------------ value(s) by xpath

    @Override
    public String[] selectValues(String xpath)
    {
        List<Node> nodes = selectNodes(xpath);
        List<String> result = new ArrayList<String>();
        for (Node currentNode : nodes)
        {
            if (currentNode.getNodeType() == NodeType.ATTRIBUTE || currentNode.getNodeType() == NodeType.TEXT)
            {
                result.add(currentNode.getNodeValue());
            }
        }
        return result.toArray(new String[] {});
    }


    @Override
    public String selectValue(String xpath)
    {
        Node singleNode = selectNode(xpath);
        if (singleNode != null
                && (singleNode.getNodeType() == NodeType.ATTRIBUTE || singleNode.getNodeType() == NodeType.TEXT))
        {
            return singleNode.getNodeValue();
        }
        return null;
    }
}
