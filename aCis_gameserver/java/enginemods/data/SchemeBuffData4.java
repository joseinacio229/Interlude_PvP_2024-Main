/*
 * L2J_EngineMods
 * Engine developed by Fissban.
 *
 * This software is not free and you do not have permission
 * to distribute without the permission of its owner.
 *
 * This software is distributed only under the rule
 * of www.devsadmins.com.
 * 
 * Contact us with any questions by the media
 * provided by our web or email marco.faccio@gmail.com
 */
package enginemods.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import enginemods.enums.BuffType;
import enginemods.holders.BuffHolder;
import net.sf.l2j.gameserver.xmlfactory.XMLDocumentFactory;

/**
 * @author fissban
 */
public class SchemeBuffData4
{
	private static final List<BuffHolder> _generalBuffs4 = new ArrayList<>();
	
	private static final List<BuffHolder> _warriorBuffs4 = new ArrayList<>();
	private static final List<BuffHolder> _mageBuffs4 = new ArrayList<>();
	
	public static void load()
	{
		// prevenimos datos duplicados en cado de recargar este metodo
		_generalBuffs4.clear();
		_warriorBuffs4.clear();
		_mageBuffs4.clear();
		
		loadBuffs4();
		loadMageBuffs4();
		loadWarriorBuffs4();
	}
	
	private static void loadBuffs4()
	{
		try
		{
			File f = new File("./data/xml/engine/scheme_buffer4/generalBuffs4.xml");
			Document doc = XMLDocumentFactory.getInstance().loadDocument(f);
			
			Node n = doc.getFirstChild();
			for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
			{
				if (d.getNodeName().equalsIgnoreCase("buff"))
				{
					NamedNodeMap attrs = d.getAttributes();
					
					BuffType type = BuffType.valueOf(attrs.getNamedItem("type").getNodeValue());
					int id = Integer.parseInt(attrs.getNamedItem("id").getNodeValue());
					int lvl = Integer.parseInt(attrs.getNamedItem("lvl").getNodeValue());
					
					_generalBuffs4.add(new BuffHolder(type, id, lvl));
				}
			}
			
			System.out.println("SchemeBuffData4: Load " + _generalBuffs4.size() + " buffs 4.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static void loadMageBuffs4()
	{
		try
		{
			File f = new File("./data/xml/engine/scheme_buffer4/setMageBuffs4.xml");
			Document doc = XMLDocumentFactory.getInstance().loadDocument(f);
			
			Node n = doc.getFirstChild();
			for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
			{
				if (d.getNodeName().equalsIgnoreCase("buff"))
				{
					NamedNodeMap attrs = d.getAttributes();
					
					int id = Integer.parseInt(attrs.getNamedItem("id").getNodeValue());
					int lvl = Integer.parseInt(attrs.getNamedItem("lvl").getNodeValue());
					
					_mageBuffs4.add(new BuffHolder(id, lvl));
				}
			}
			
			System.out.println("SchemeBuffData4: Load " + _mageBuffs4.size() + " set mage buffs 4.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static void loadWarriorBuffs4()
	{
		try
		{
			File f = new File("./data/xml/engine/scheme_buffer4/setWarriorBuffs4.xml");
			Document doc = XMLDocumentFactory.getInstance().loadDocument(f);
			
			Node n = doc.getFirstChild();
			for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
			{
				if (d.getNodeName().equalsIgnoreCase("buff"))
				{
					NamedNodeMap attrs = d.getAttributes();
					
					int id = Integer.parseInt(attrs.getNamedItem("id").getNodeValue());
					int lvl = Integer.parseInt(attrs.getNamedItem("lvl").getNodeValue());
					
					_warriorBuffs4.add(new BuffHolder(id, lvl));
				}
			}
			
			System.out.println("SchemeBuffData4: Load " + _warriorBuffs4.size() + " set warrior buffs 4.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static List<BuffHolder> getAllMageBuffs4()
	{
		return _mageBuffs4;
	}
	
	public static List<BuffHolder> getAllWarriorBuffs4()
	{
		return _warriorBuffs4;
	}
	
	public static List<BuffHolder> getAllGeneralBuffs4()
	{
		return _generalBuffs4;
	}
}
