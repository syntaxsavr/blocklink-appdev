import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.groupthree.blocklink.CustomChatAdapter
import com.groupthree.blocklink.Message
import com.groupthree.blocklink.MessageType
import com.groupthree.blocklink.R

class ChatFragment : Fragment() {

    private val messageList = createDummyMessageList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_chat, container, false)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = CustomChatAdapter(messageList)

        return rootView
    }

    private fun createDummyMessageList(): List<Message> {
        return listOf(
            Message("John", "2023-07-30 12:30 PM", "Hello!", MessageType.INCOMING),
            Message("Alex", "2023-07-30 12:35 PM", "Hi John!", MessageType.OUTGOING),
            Message("John", "2023-07-30 12:37 PM", "How are you?", MessageType.INCOMING),
            Message("Alex", "2023-07-30 12:40 PM", "I'm doing well, thanks!", MessageType.OUTGOING),
            Message("John", "2023-07-30 12:42 PM", "That's great!", MessageType.INCOMING),
            Message("Alex", "2023-07-30 12:45 PM", "Yes, it is!", MessageType.OUTGOING)
        )
    }
}
